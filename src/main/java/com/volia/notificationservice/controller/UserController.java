package com.volia.notificationservice.controller;

import com.volia.notificationservice.core.common.dto.GenericCreationResponse;
import com.volia.notificationservice.model.dto.UserCreationRequestDto;
import com.volia.notificationservice.service.User.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<GenericCreationResponse> createNewUser(@Valid @RequestBody UserCreationRequestDto userCreationRequestDto) {
        return new ResponseEntity<>(this.userService.createUser(userCreationRequestDto), HttpStatus.CREATED);
    }


}
