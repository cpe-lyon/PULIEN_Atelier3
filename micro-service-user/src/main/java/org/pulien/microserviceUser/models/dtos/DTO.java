package org.pulien.microserviceUser.models.dtos;

public interface DTO<T> {
    T toEntity();
}
