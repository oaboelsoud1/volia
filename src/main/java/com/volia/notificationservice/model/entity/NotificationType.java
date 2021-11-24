package com.volia.notificationservice.model.entity;

import java.util.stream.Stream;

public enum NotificationType {

    EMAIL("EMAIL"),
    SMS("SMS");

    private final String databaseValue;

    NotificationType(String databaseValue) {
        this.databaseValue = databaseValue;
    }

    public String getDatabaseValue() {
        return databaseValue;
    }

    public static NotificationType of(String databaseValue) {
        return Stream.of(NotificationType.values())
                .filter(paymentStatus -> paymentStatus.getDatabaseValue().equals(databaseValue))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
