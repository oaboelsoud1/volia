package com.volia.notificationservice.model.repository;

import com.volia.notificationservice.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends PagingAndSortingRepository<User, Long> {

    @Query("select distinct (u) from User u " +
            " inner join City c on c.id = u.city.id" +
            " where " +
            " (coalesce(:cityName) is not null and c.name = :cityName) "
            + " and (:userNameFirstLetter) IS null or u.name LIKE :userNameFirstLetter% "
            + " and u.id != u.notificationLog.userId.id ")
    Page<User> getCustomersByFilters(String cityName, String userNameFirstLetter, Pageable pageable);

}
