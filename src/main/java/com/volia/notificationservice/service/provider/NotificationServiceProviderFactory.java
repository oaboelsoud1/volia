package com.volia.notificationservice.service.provider;

import com.volia.notificationservice.model.entity.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceProviderFactory {

    public static NotificationServiceProvider getInstance(NotificationType notificationType) {
        switch (notificationType) {
            case EMAIL:
                return new EmailService();
            case SMS:
                return new SmsService();
            default:
                break;
        }
        //error pone gedan bs w2at b2a hn3ml eh :D
        return new EmailService();
    }

}
