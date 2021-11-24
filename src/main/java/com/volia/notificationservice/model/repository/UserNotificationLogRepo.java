package com.volia.notificationservice.model.repository;

import com.volia.notificationservice.model.entity.UserNotificationLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationLogRepo extends CrudRepository<UserNotificationLog,Long> {
}
