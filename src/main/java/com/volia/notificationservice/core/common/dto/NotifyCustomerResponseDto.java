package com.volia.notificationservice.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NotifyCustomerResponseDto {
    Integer totalNumberOfNotifiedCustomer;
}
