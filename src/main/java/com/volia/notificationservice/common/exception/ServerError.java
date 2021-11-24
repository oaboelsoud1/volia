package com.volia.notificationservice.common.exception;

import org.springframework.http.HttpStatus;

public enum ServerError {


    //user
    USER_CREATION_FAILED("Failed to create new user"),
    GET_USERS_FAILED("Failed to get Users"),

    //City
    CITY_CREATION_FAILED("Failed to create new city"),
    CITY_NOT_EXIST("The city is not exist in our records",HttpStatus.NO_CONTENT),
    GET_CITY_BY_NAME_FAILED("Failed to get city by name"),

    //Notification

    NOTIFICATION_CREATION_FAILED("Failed to create new Notification"),
    NOTIFY_CUSTOMER_FAILED("failed to notify customer"),

    BASE_ERROR("TEST", Type.WARNING, HttpStatus.BAD_GATEWAY, true),

    //paging
    PAGE_INVALID("Invalid Page"),
    INTERNAL_SERVER_ERROR("internal server error");


    public enum Type {
        ERROR, WARNING, INFO;
    }

    private Type type;
    private transient HttpStatus status;
    private boolean notifyDevelopers;
    private String message;

    private ServerError(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    private ServerError(String message, HttpStatus status) {
        this(message, Type.ERROR, status, false);
    }

    private ServerError(String message, Type t) {
        this(message, t, HttpStatus.BAD_REQUEST, false);
    }

    private ServerError(String message, Type t, HttpStatus status) {
        this(message, t, status, false);
    }

    private ServerError(String message, Type t, HttpStatus status, boolean notifyDevloper) {
        this.type = t;
        this.status = status;
        this.notifyDevelopers = notifyDevloper;
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public boolean isNotifyDevelopers() {
        return notifyDevelopers;
    }

    public void setNotifyDevelopers(boolean notifyDevelopers) {
        this.notifyDevelopers = notifyDevelopers;
    }

    public String getMessage() {
        return message;
    }
}
