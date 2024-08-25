package com.identify.identify.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.identify.identify.dto.IdentityResponseDTO;
import com.identify.identify.dto.NewIdentityRequest;
import com.identify.identify.dto.ReturnResponse;
import com.identify.identify.entity.Identify;
import com.identify.identify.entity.Identity;
import com.identify.identify.entity.User;
import com.identify.identify.error.ApiRequestException;
import com.identify.identify.helper.DataEncoder;
import com.identify.identify.helper.PremblyManager;
import com.identify.identify.helper.mapper.IdentityMapper;
import com.identify.identify.repository.IdentityRepository;

@Service
public class IdentityService {

    @Autowired
    private IdentityRepository identityRepository; 

    @Autowired
    private PremblyManager premblyManager;



    public List<IdentityResponseDTO> getAllIdentity(User user){
        List<IdentityResponseDTO> identities = identityRepository.findByUser(user)
                    .stream()
                    .map(IdentityMapper::toDTO)
                    .collect(Collectors.toList());
        return identities;
    }

    public Object addNewIdentity(NewIdentityRequest request, User user) throws Exception{

        List<String> allowedValues = Arrays.asList("BVN", "NIN", "PHONE", "DRIVERS_LICENSE");

        if(!allowedValues.contains(request.getIdType())){
            throw new ApiRequestException(
                "Invalid ID type, available choices are " + allowedValues.toString()
            );
        }
        
        List<Identity> userIds = identityRepository.findByUser(user);

        for (Identity identity : userIds) {
            if (identity.idType.equals(request.getIdType())) {
                throw new ApiRequestException("ID type already registered");
            }
        }
        // System.out.println(userIds);

        ReturnResponse premblyResponse = premblyManager.verifyPhoneNumber(request.getNumber(), "NG");
        System.out.println(premblyResponse);

        if (premblyResponse.getStatus().equals(true)){
            Identity identity = new Identity();
            identity.setActive(true);
            identity.setIdType(request.getIdType());
            identity.setIdNumber(request.getNumber());
            identity.setUser(user);
            identity.setRecord(DataEncoder.encode(premblyResponse.getData()));
            identityRepository.save(identity);
            return "Record added";
        }
        
        throw new ApiRequestException(
            premblyResponse.getMessage()
        );
    }
    
}
