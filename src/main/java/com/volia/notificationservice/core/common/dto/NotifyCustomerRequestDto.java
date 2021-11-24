package com.volia.notificationservice.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
@NoArgsConstructor
@AllArgsConstructor
@Valid
@Builder
@Data
public class NotifyCustomerRequestDto {
    String userNameFirstLetter;
    String cityName;

    @NotBlank(message = "Notification should not be blank")
    String notificationCode;
}
