package com.volia.notificationservice.service.provider;

import org.springframework.stereotype.Service;

@Service
public class EmailService implements NotificationServiceProvider{

    @Override
    public void sendNotification(String customerName, String notificationCode) {

    }
}
