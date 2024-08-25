package com.identify.identify.helper.mapper;


import com.identify.identify.dto.IdentityResponseDTO;
import com.identify.identify.entity.Identity;

public class IdentityMapper {


    public static IdentityResponseDTO toDTO(Identity identity) {
        IdentityResponseDTO dto = new IdentityResponseDTO();
        dto.setId(identity.getId());
        dto.setIdType(identity.getIdType());
        dto.setIdNumber(identity.getIdNumber());
        dto.setRecord(identity.getRecord());
        dto.setActive(identity.isActive());
        dto.setCreatedAt(identity.getCreatedAt());
        dto.setUpdatedAt(identity.getUpdatedAt());
        dto.setActive(identity.isActive());
        return dto;
    }
}
