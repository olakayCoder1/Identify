package com.identify.identify.helper.mapper;

import com.identify.identify.dto.AnonymousIdentityResponseDTO;
import com.identify.identify.dto.IdentityResponseDTO;
import com.identify.identify.dto.shares.AnonymousViewResponseDTO;
import com.identify.identify.entity.IdentityShare;
import com.identify.identify.entity.Identity;

public class AnonymousIdentitySharesMapper {

    public static AnonymousViewResponseDTO toDTO(IdentityShare identityShare) {
        AnonymousViewResponseDTO dto = new AnonymousViewResponseDTO();
        dto.setIdentifier(identityShare.getIdentifier());
        dto.setEmails(identityShare.getEmails());

        Identity identity = identityShare.getIdentity();
        if (identity != null) {
            AnonymousIdentityResponseDTO identityDTO = AnonymousIdentityMapper.toDTO(identity);
            dto.setIdentity(identityDTO);
        }

        return dto;
    }
}