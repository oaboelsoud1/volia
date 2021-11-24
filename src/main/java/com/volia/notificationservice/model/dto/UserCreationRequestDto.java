package com.volia.notificationservice.model.dto;

import com.volia.notificationservice.model.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserCreationRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String cityName;

    @NotNull
    private NotificationType notificationType;
}
