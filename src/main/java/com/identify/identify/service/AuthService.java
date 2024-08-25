package com.identify.identify.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.identify.identify.dto.auth.AccountAtivationRequest;
import com.identify.identify.dto.auth.AuthenticationRequest;
import com.identify.identify.dto.auth.AuthenticationResponse;
import com.identify.identify.dto.auth.RegisterRequest;
import com.identify.identify.dto.auth.RegisterResponse;
import com.identify.identify.entity.ActivationCode;
import com.identify.identify.entity.Role;
import com.identify.identify.entity.User;
import com.identify.identify.error.ApiRequestException;
import com.identify.identify.helper.mail.EmailSender;
import com.identify.identify.repository.UserRepository;

import jakarta.validation.Valid;

import com.identify.identify.repository.ActivationCodeRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {


    @Autowired
    private EmailSender emailService;

    private final UserRepository repository;

    private final ActivationCodeRepository activationCodeRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;


    private final AuthenticationManager authenticationManager;
   

    
    public RegisterResponse register(RegisterRequest request) {

        // Check if email already exists
        if (repository.findByEmail(request.getEmail()).isPresent()) {

            throw new ApiRequestException("Email is already in use");
        }
        
        var user = User
                        .builder()
                        .email(request.getEmail())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .isVerify(false)
                        .isActive(false)
                        .role(Role.USER)
                        .build();
        
        repository.save(user);

        // var jwtToken = jwtService.generateToken(user);

        String activationCode = generateRefKey();

        var activationCodeObj = ActivationCode
                                .builder()
                                .code(activationCode)
                                .user(user)
                                .isValid(true)
                                .build();
        activationCodeRepository.save(activationCodeObj);


        System.out.println("************************************************************************************************************");
        System.out.println(activationCode);
        System.out.println("************************************************************************************************************");

        try {
            emailService.sendAccountVerificationMial(request.getEmail(), activationCode);
        } catch (Exception e) {
            System.out.println(e);
        }
        

        return new RegisterResponse("Account created", user);
    }


    
    public Object accountActivate(@Valid AccountAtivationRequest request) {

        var existed = repository.findByEmail(request.getEmail());
        
        if (!existed.isPresent()){
            throw new ApiRequestException("Invalid activation code");
        }

        var codeExisted = activationCodeRepository.findByCode(request.getCode());
        if (!codeExisted.isPresent()){
            throw new ApiRequestException("Invalid activation code");
        }

        var code = codeExisted.orElseThrow();
        if (!code.isValid() ){
            throw new ApiRequestException("Invalid activation code");
        }

        var user = existed.orElseThrow();
        user.setIsVerify(true);
        user.setIsActive(true);

        code.setValid(false);
        activationCodeRepository.save(code);
        repository.save(user);
        return new RegisterResponse("You account has been verify successfully", null);
    }

 
 
    public AuthenticationResponse authlogin(AuthenticationRequest request) {

        var existed = repository.findByEmail(request.getEmail());
        if (!existed.isPresent()){
            throw new ApiRequestException("Invalid credentials");
        }

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword())
        );

        var user = existed.orElseThrow();
        if( !user.getIsVerify().equals(true)){
            throw new ApiRequestException("Your account is not yet verified. Kindly verify to continue enjoying our service");
        }
        if( !user.getIsActive().equals(true)){
            throw new ApiRequestException("Your account is disabled. Kindly reach out to the management to enable your account");
        }
        
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token((String) jwtToken).build();

    }




    public String generateRefKey() {
        String generatedKey;
        do {
            generatedKey = generateUniqueKey();
        } while (activationCodeRepository.existsByCode(generatedKey));
        return generatedKey;
    }

    private String generateUniqueKey() {
        StringBuilder builder = new StringBuilder();
        String baseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        builder.append(Long.toString(System.currentTimeMillis(), 36).substring(0, 2));

        for (int i = 0; i < 4; i++) {
            int randomIndex = (int) (Math.random() * baseString.length());
            builder.append(baseString.charAt(randomIndex));
        }

        return builder.toString();
    }



    

    
}