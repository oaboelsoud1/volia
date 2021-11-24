package com.volia.notificationservice.model.entity.converter;

import com.volia.notificationservice.model.entity.NotificationType;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class NotificationTypeConverter implements AttributeConverter<NotificationType, String> {

    @Override
    public String convertToDatabaseColumn(NotificationType notificationType) {
        if (Objects.nonNull(notificationType)) {
            return notificationType.getDatabaseValue();
        }
        return null;
    }

    @Override
    public NotificationType convertToEntityAttribute(String databaseValue) {
        if (StringUtils.isNotEmpty(databaseValue)) {
            return NotificationType.of(databaseValue);
        }
        return null;
    }
}
