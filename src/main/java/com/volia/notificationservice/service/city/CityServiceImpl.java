package com.volia.notificationservice.service.city;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import com.volia.notificationservice.common.exception.LogicalException;
import com.volia.notificationservice.common.exception.ServerError;
import com.volia.notificationservice.core.common.dto.GenericCreationResponse;
import com.volia.notificationservice.model.dto.CityCreationRequestDto;
import com.volia.notificationservice.model.entity.City;
import com.volia.notificationservice.model.repository.CityRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
    private final CityRepo cityRepo;

    public CityServiceImpl(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    @Override
    public City getCityByName(String name) throws LogicalException {
        try{
            Optional<City> city = cityRepo.getCityByName(name);
            if (city.isEmpty()) {
                logger.info().message("City not exist in our records").log();
                throw new LogicalException(ServerError.CITY_NOT_EXIST);
            }
            return city.get();
        }catch (LogicalException logicalException){
            logger.error().exception("Logical exception happen while get  city by name",logicalException)
                    .field("name",name)
                    .log();
            throw logicalException;
        }catch (Exception e){
            logger.error().exception("Exception happen while while get  city by name",e)
                    .field("name",name)
                    .log();
            throw new LogicalException(ServerError.GET_CITY_BY_NAME_FAILED);
        }

    }

    @Override
    public GenericCreationResponse createCity(CityCreationRequestDto cityCreationRequestDto) {
        try{
            City city = City.builder().name(cityCreationRequestDto.getName()).build();
            cityRepo.save(city);
            // create code for each city
            return GenericCreationResponse.builder().content(city.getName()).build();
        }catch (LogicalException logicalException){
            logger.error().message("Logical exception happen while creating new city")
                    .field("name",cityCreationRequestDto.getName())
                    .log();
            throw logicalException;
        }catch (Exception e){
            logger.error().exception("Exception happen while creating new city",e)
                    .field("name",cityCreationRequestDto.getName())
                    .log();
            throw new LogicalException(ServerError.CITY_CREATION_FAILED);
        }
    }
}
