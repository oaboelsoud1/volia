package com.volia.notificationservice.service.city;

import com.volia.notificationservice.core.common.dto.GenericCreationResponse;
import com.volia.notificationservice.model.dto.CityCreationRequestDto;
import com.volia.notificationservice.model.entity.City;

public interface CityService {

    City getCityByName(String name);
    GenericCreationResponse createCity(CityCreationRequestDto cityCreationRequestDto);
}
