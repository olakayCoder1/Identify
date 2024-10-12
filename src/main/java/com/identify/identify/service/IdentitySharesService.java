package com.identify.identify.service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.identify.identify.dto.shares.AnonymousViewResponseDTO;
import com.identify.identify.dto.shares.IdentitySharesResponseDTO;
import com.identify.identify.entity.IdentityShare;
import com.identify.identify.entity.User;
import com.identify.identify.error.ApiRequestException;
import com.identify.identify.error.UnauthorizedAccessException;
import com.identify.identify.helper.mapper.AnonymousIdentitySharesMapper;
import com.identify.identify.helper.mapper.IdentitySharesMapper;
import com.identify.identify.repository.IdentityRepository;
import com.identify.identify.repository.IdentityShareRepository;

@Service
public class IdentitySharesService {


    @Autowired
    private IdentityShareRepository identityShareRepository;


    @Autowired
    private IdentityRepository identityRepository;


    

    public List<IdentitySharesResponseDTO> getAllShares(User user){
        List<IdentitySharesResponseDTO> identityShares = identityShareRepository.findByUser(user)
                    .stream()
                    .map(IdentitySharesMapper::toDTO)
                    .collect(Collectors.toList());
        return identityShares;
    }
    
    
    public IdentitySharesResponseDTO createNewShareLink(User user, Integer identityId, List<String> emails){
        var identityOptional = identityRepository.findById(identityId);
        
        var identity = identityOptional.orElseThrow(() -> new ApiRequestException("Identity not found with id: " + identityId));

        if (identity.getUser().equals(user)){
            System.out.println(identity);
            System.out.println(identityId);
            System.out.println(emails);
            IdentityShare identityShare = new IdentityShare();
            identityShare.setUser(user);
            identityShare.setActive(true);
            identityShare.setEmails(emails);
            identityShare.setIdentity(identity);
            identityShareRepository.save(identityShare);


            return IdentitySharesMapper.toDTO(identityShare);
        }
        else {
            throw new UnauthorizedAccessException("You do not have permission to share this identity.");
        }

    }


    public IdentitySharesResponseDTO getSingleShareLink(User user, Integer identityId) {
        // Fetch the IdentityShare object from the repository
        var identityOptional = identityShareRepository.findById(identityId);
    
        // Check if the identity exists and if the user is authorized to access it
        var identityShare = identityOptional.orElseThrow(() -> 
            new ApiRequestException("Identity share not found with id: " + identityId)
        );

        // Check if the user is the owner of the IdentityShare
        if (identityShare.getUser().equals(user)) {

            // If the identifier is null, generate a new one
            if (identityShare.getIdentifier() == null || identityShare.getIdentifier().toString().equals("00000000-0000-0000-0000-000000000000")) {
                identityShare.setIdentifier(UUID.randomUUID());  // Generate a new UUID
                identityShareRepository.save(identityShare);
                System.out.println("Generated and saved new identifier: " + identityShare.getIdentifier());
            }
            // Map the IdentityShare to a DTO and return it
            return IdentitySharesMapper.toDTO(identityShare);
        } else {
            throw new UnauthorizedAccessException("You do not have permission to access this identity.");
        }
    }


    public AnonymousViewResponseDTO viewShareLink(UUID identifier) {
        // Fetch the IdentityShare object from the repository
        var identityOptional = identityShareRepository.findByIdentifier(identifier);
    
        // Check if the identity exists
        var identityShare = identityOptional.orElseThrow(() -> 
            new ApiRequestException("Identity share not found with identifier: " + identifier)
        );

        // If the identity share exists, map it to DTO and return it
        return AnonymousIdentitySharesMapper.toDTO(identityShare);
    }


    public IdentitySharesResponseDTO updateSingleShareLink(User user, Integer identityId , Boolean status) {
        // Fetch the IdentityShare object from the repository
        var identityOptional = identityShareRepository.findById(identityId);
    
        // Check if the identity exists and if the user is authorized to access it
        var identityShare = identityOptional.orElseThrow(() -> 
            new ApiRequestException("Identity share not found with id: " + identityId)
        );

        // Check if the user is the owner of the IdentityShare
        if (identityShare.getUser().equals(user)) {
            // Map the IdentityShare to a DTO and return it
            identityShare.setActive(status);
            identityShareRepository.save(identityShare);
            return IdentitySharesMapper.toDTO(identityShare);
        } else {
            throw new UnauthorizedAccessException("You do not have permission to access this identity.");
        }
    }


    public IdentitySharesResponseDTO deleteSingleShareLink(User user, Integer identityId) {
        // Fetch the IdentityShare object from the repository
        var identityOptional = identityShareRepository.findById(identityId);
    
        // Check if the identity exists and if the user is authorized to access it
        var identityShare = identityOptional.orElseThrow(() -> 
            new ApiRequestException("Identity share not found with id: " + identityId)
        );


        System.out.println(identityShare.getIdentity());
        System.out.println(identityShare.getIdentity());
    
        // Check if the user is the owner of the IdentityShare
        if (identityShare.getUser().equals(user)) {
            identityShareRepository.delete(identityShare);
            return IdentitySharesMapper.toDTO(identityShare);
        } else {
            throw new UnauthorizedAccessException("You do not have permission to access this identity.");
        }
    }
    
    
    
}
