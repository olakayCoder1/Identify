package com.identify.identify.helper.mapper;

import com.identify.identify.dto.IdentityResponseDTO;
import com.identify.identify.dto.shares.IdentitySharesResponseDTO;
import com.identify.identify.entity.IdentityShare;
import com.identify.identify.entity.Identity;

public class IdentitySharesMapper {

    public static IdentitySharesResponseDTO toDTO(IdentityShare identityShare) {
        IdentitySharesResponseDTO dto = new IdentitySharesResponseDTO();
        dto.setId(identityShare.getId());
        dto.setIdentifier(identityShare.getIdentifier());
        dto.setEmails(identityShare.getEmails());
        dto.setActive(identityShare.isActive());
        dto.setCreatedAt(identityShare.getCreatedAt());
        dto.setUpdatedAt(identityShare.getUpdatedAt());

        Identity identity = identityShare.getIdentity();
        if (identity != null) {
            IdentityResponseDTO identityDTO = IdentityMapper.toDTO(identity);
            dto.setIdentity(identityDTO);
        }

        return dto;
    }
}