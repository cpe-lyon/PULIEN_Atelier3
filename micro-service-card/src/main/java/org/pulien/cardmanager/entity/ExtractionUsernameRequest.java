package org.pulien.cardmanager.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExtractionUsernameRequest {
    private String token;
}
