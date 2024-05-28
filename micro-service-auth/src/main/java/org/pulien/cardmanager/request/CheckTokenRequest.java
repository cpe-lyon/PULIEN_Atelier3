package org.pulien.cardmanager.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckTokenRequest {
    private String token;
}
