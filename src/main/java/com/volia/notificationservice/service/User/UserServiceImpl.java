package com.volia.notificationservice.service.User;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import com.volia.notificationservice.common.exception.LogicalException;
import com.volia.notificationservice.common.exception.ServerError;
import com.volia.notificationservice.core.common.dto.GenericCreationResponse;
import com.volia.notificationservice.model.dto.UserCreationRequestDto;
import com.volia.notificationservice.model.entity.City;
import com.volia.notificationservice.model.entity.User;
import com.volia.notificationservice.model.repository.UserRepo;
import com.volia.notificationservice.service.city.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final CityService cityService;
    private final UserRepo userRepo;

    public UserServiceImpl(CityService cityService, UserRepo userRepo) {
        this.cityService = cityService;
        this.userRepo = userRepo;
    }

    @Override
    public GenericCreationResponse createUser(UserCreationRequestDto userCreationRequestDto) {
        try {
            logger.info().message("create new user").log();
            City city = cityService.getCityByName(userCreationRequestDto.getCityName());
            User user = User.builder().name(userCreationRequestDto.getName())
                    .notificationType(userCreationRequestDto.getNotificationType())
                    .city(city).build();

            userRepo.save(user);

            logger.info().message("User has been created successfully")
                    .field("name", user.getName())
                    .field("city", user.getCity())
                    .log();
            return GenericCreationResponse.builder()
                    .content(user.getName())
                    .id(user.getId())
                    .build();

        } catch (LogicalException e) {
            logger.error().exception("Logical exception happened while creating new user", e)
                    .field("name", userCreationRequestDto.getName()).log();
            throw new LogicalException(ServerError.USER_CREATION_FAILED);
        } catch (Exception e) {
            logger.error().exception("Exception happened while creating new user", e)
                    .field("name", userCreationRequestDto.getName()).log();
            throw new LogicalException(ServerError.USER_CREATION_FAILED);
        }
    }

    @Override
    public Page<User> getUsers(String cityName, String userNameFirstLetter, Pageable pageable) {
        try {
            logger.info().message("get users")
                    .field("cityName", cityName)
                    .field("userNameFirstLetter", userNameFirstLetter)
                    .field("size", pageable.getPageSize())
                    .field("page", pageable.getPageNumber())
                    .log();


            Page<User> userPage = userRepo.getCustomersByFilters(cityName, userNameFirstLetter, pageable);
            logger.info().message("Users has been retrieved Successfully")
                    .field("totalNumCustomer", userPage.getNumberOfElements()).log();
            return userPage;
        } catch (LogicalException logicalException) {
            logger.error().exception("Logical Exception happened while getting customers", logicalException)
                    .field("cityName", cityName)
                    .field("userNameFirstLetter", userNameFirstLetter)
                    .field("size", pageable.getPageSize())
                    .field("page", pageable.getPageNumber())
                    .log();
            throw logicalException;

        } catch (Exception e) {
            logger.error().exception("Exception happened while getting customers", e)
                    .field("cityName", cityName)
                    .field("userNameFirstLetter", userNameFirstLetter)
                    .field("size", pageable.getPageSize())
                    .field("page", pageable.getPageNumber())
                    .log();
            throw new LogicalException(ServerError.GET_USERS_FAILED);

        }
    }


}
