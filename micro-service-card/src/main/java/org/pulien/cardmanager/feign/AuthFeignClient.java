package org.pulien.cardmanager.feign;

import org.apache.coyote.BadRequestException;
import org.pulien.cardmanager.entity.ExtractionUsernameRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthFeignClient {
    String extractUsernameFromJWT(@RequestBody ExtractionUsernameRequest token) throws BadRequestException;
    Long extractUserIdFromJWT(@RequestBody ExtractionUsernameRequest token) throws BadRequestException;
}
