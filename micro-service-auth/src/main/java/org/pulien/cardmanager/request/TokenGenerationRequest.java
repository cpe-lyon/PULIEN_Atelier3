package org.pulien.cardmanager.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenGenerationRequest {
    private String username;
}
