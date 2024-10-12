package com.identify.identify.dto.shares;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareIdentityRequest {
    
    private List<String> emails; 

    @NotNull(message = "identity id cannot be null")
    private Integer indentityId;

    @NotNull(message = "viewOnce cannot be null")
    private Boolean viewOnce;
}
