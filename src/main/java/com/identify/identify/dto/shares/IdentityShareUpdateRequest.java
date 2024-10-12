package com.identify.identify.dto.shares;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentityShareUpdateRequest {


    @NotNull(message = "status cannot be null")
    private Boolean status;
    
}
