package com.volia.notificationservice.controller;

import com.volia.notificationservice.core.common.dto.*;
import com.volia.notificationservice.service.notifcation.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    //notify should be pleurer But nothing comes to my mind
    @PostMapping("/notify")
        public ResponseEntity<NotifyCustomerResponseDto> notifyCustomer(@Valid @RequestBody NotifyCustomerRequestDto notifyCustomerRequestDto){
        return new ResponseEntity<>(notificationService.notifyCustomers(notifyCustomerRequestDto), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<GenericCreationResponse> createNewNotification(@Valid  @RequestBody CreateNotificationRequestDto createNotificationRequestDto){
        return new ResponseEntity<>(notificationService.createNotification(createNotificationRequestDto),HttpStatus.CREATED);
    }


}
