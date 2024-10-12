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
public class IdentityUpdateDto {
    
    @NotNull(message = "Active cannot be null")
    private Boolean active;
}
