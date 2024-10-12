package com.identify.identify.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.identify.identify.dto.IdentityResponseDTO;
import com.identify.identify.dto.NewIdentityRequest;
import com.identify.identify.dto.ReturnResponse;
import com.identify.identify.entity.Identity;
import com.identify.identify.entity.IdentityShare;
import com.identify.identify.entity.User;
import com.identify.identify.error.ApiRequestException;
import com.identify.identify.helper.DataEncoder;
import com.identify.identify.helper.PremblyManager;
import com.identify.identify.helper.mapper.IdentityMapper;
import com.identify.identify.repository.IdentityRepository;
import com.identify.identify.repository.IdentityShareRepository;

@Service
public class IdentityService {

    @Autowired
    private IdentityRepository identityRepository; 

    @Autowired
    private IdentityShareRepository identityShareRepository;

    @Autowired
    private PremblyManager premblyManager;

    



    public List<IdentityResponseDTO> getAllIdentity(User user){
        List<IdentityResponseDTO> identities = identityRepository.findByUser(user)
                    .stream()
                    .map(IdentityMapper::toDTO)
                    .collect(Collectors.toList());
        return identities;
    }
    
    
    public Object addNewIdentity(NewIdentityRequest request, User user) throws Exception {

        // Use enum for ID types
        List<String> allowedValues = Arrays.asList(IdType.BVN.name(), IdType.NIN.name(), IdType.PHONE.name(), IdType.DRIVERS_LICENSE.name());
    
        // Validate the ID type
        if (!allowedValues.contains(request.getIdType())) {
            throw new ApiRequestException(
                "Invalid ID type, available choices are " + allowedValues.toString()
            );
        }
    
        // Check if the user already has this ID type
        List<Identity> userIds = identityRepository.findByUser(user);
        for (Identity identity : userIds) {
            if (identity.getIdType().equals(request.getIdType())) {
                throw new ApiRequestException("ID type already registered");
            }
        }
    
        ReturnResponse premblyResponse;
        switch (IdType.valueOf(request.getIdType())) {
            case BVN:
                premblyResponse = premblyManager.verifyBVN(request.getNumber(), "NG");
                break;
            case NIN:
                premblyResponse = premblyManager.verifyNIN(request.getNumber(), "NG");
                break;
            case PHONE:
                premblyResponse = premblyManager.verifyPhoneNumber(request.getNumber(), "NG");
                break;
            case DRIVERS_LICENSE:
                premblyResponse = premblyManager.verifyDriversLicense(request.getNumber(), "NG");
                break;
            default:
                throw new ApiRequestException("Invalid ID type");
        }
    
        // Process response
        if (premblyResponse.getStatus()) {
            Identity identity = new Identity();
            identity.setActive(true);
            identity.setIdType(request.getIdType());
            identity.setIdNumber(request.getNumber());
            identity.setUser(user);
            identity.setRecord(DataEncoder.encode(premblyResponse.getData()));
            identityRepository.save(identity);
            return "Record added successfully";
        } else {
            throw new ApiRequestException(premblyResponse.getMessage());
        }
    }
    
    public enum IdType {
        BVN,
        NIN,
        PHONE,
        DRIVERS_LICENSE
    }
    

    public IdentityResponseDTO getSingleIdentity(Integer identityId){

        var identityOptional = identityRepository.findById(identityId);
        System.out.println(identityOptional);

        return identityOptional
            .map(IdentityMapper::toDTO)
            .orElseThrow(
                () -> new ApiRequestException("Identity not found with id: " + identityId)
            );
    }


    public IdentityResponseDTO updateSingleIdentity(Integer identityId, Boolean isActive){

        var identityOptional = identityRepository.findById(identityId);
        System.out.println(identityOptional);

        var identity = identityOptional.orElseThrow();
        identity.setActive(isActive);
        identityRepository.save(identity);

        return identityOptional
            .map(IdentityMapper::toDTO)
            .orElseThrow(
                () -> new ApiRequestException("Identity not found with id: " + identityId)
            );
    }



    public Object generateSharebaleLinkForIdentity(User user, Integer identityId, List<String> emails){
                              
        var identityOptional = identityRepository.findById(identityId);

        var identity = identityOptional.orElseThrow();
        if (identity.getUser().equals(user)){
            System.out.println(identity);
            System.out.println(identityId);
            System.out.println(emails);
            IdentityShare identityShare = new IdentityShare();
            identityShare.setUser(user);
            identityShare.setActive(true);
            identityShare.setEmails(emails);
            identityShareRepository.save(identityShare);
        }
        
        return "";
        
    }
}
