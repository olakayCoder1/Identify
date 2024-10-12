package com.identify.identify.helper.mapper;


import com.identify.identify.dto.AnonymousIdentityResponseDTO;
import com.identify.identify.entity.Identity;

public class AnonymousIdentityMapper {


    public static AnonymousIdentityResponseDTO toDTO(Identity identity) {
        AnonymousIdentityResponseDTO dto = new AnonymousIdentityResponseDTO();
        dto.setRecord(identity.getRecord());
        return dto;
    }
}
