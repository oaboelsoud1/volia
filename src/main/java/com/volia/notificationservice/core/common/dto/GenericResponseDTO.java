package com.volia.notificationservice.core.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponseDTO {
    private String message;

    public GenericResponseDTO(String message) {
        this.message = message;
    }

}
