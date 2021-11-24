package com.volia.notificationservice.service.User;

import com.volia.notificationservice.common.exception.LogicalException;
import com.volia.notificationservice.core.common.dto.GenericCreationResponse;
import com.volia.notificationservice.model.dto.UserCreationRequestDto;
import com.volia.notificationservice.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    GenericCreationResponse createUser(UserCreationRequestDto userCreationRequestDto) throws LogicalException;
    Page<User> getUsers(String cityName , String userNameFirstLetter, Pageable pageable);
}
