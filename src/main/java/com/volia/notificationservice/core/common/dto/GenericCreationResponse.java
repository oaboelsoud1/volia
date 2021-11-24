package com.volia.notificationservice.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericCreationResponse {
    private String content;
    private String code;
    // DB id should not be exposed in response . it should be replaced with code . bs requirement b2a bt2ol keda :D
    private Long id ;
}
