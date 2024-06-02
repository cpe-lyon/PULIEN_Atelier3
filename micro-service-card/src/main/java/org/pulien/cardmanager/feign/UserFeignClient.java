package org.pulien.cardmanager.feign;

import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserFeignClient {
    Long getUserIdByLogin(@PathVariable String userLogin) throws BadRequestException;

    void creditUser(@PathVariable int amount, @PathVariable Long userToUpdateId) throws BadRequestException;

    void debitUser(@PathVariable int amount, @PathVariable Long userToUpdateId) throws BadRequestException;
}
