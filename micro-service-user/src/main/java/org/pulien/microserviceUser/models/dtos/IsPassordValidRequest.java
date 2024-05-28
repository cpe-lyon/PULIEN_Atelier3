package org.pulien.microserviceUser.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IsPassordValidRequest {
    private String password;
    private String username;
}
