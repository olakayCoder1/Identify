package com.identify.identify.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewIdentityRequest {
    
    @NotNull(message = "idType cannot be null")
    private String idType;

    @NotNull(message = "Number cannot be null")
    private String number;

}
