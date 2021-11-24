package com.volia.notificationservice.controller;

import com.volia.notificationservice.core.common.dto.GenericCreationResponse;
import com.volia.notificationservice.model.dto.CityCreationRequestDto;
import com.volia.notificationservice.service.city.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/cities")
public class CityController {

    @Autowired
    CityService cityService;
    @PostMapping
    public ResponseEntity<GenericCreationResponse> createCity( @Valid  @RequestBody  CityCreationRequestDto cityCreationRequestDto){
        return new ResponseEntity<>(cityService.createCity(cityCreationRequestDto), HttpStatus.CREATED);
    }
}
