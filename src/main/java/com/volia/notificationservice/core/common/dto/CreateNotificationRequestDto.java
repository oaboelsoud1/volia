package com.volia.notificationservice.core.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class CreateNotificationRequestDto {
    @NotBlank(message = "Notification should have a text")
    String text;
}
