package com.volia.notificationservice.model.repository;

import com.volia.notificationservice.model.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepo extends CrudRepository<City, Long> {

    Optional<City> getCityByName(String name);
}
