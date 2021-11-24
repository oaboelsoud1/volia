package com.volia.notificationservice.model.repository;

import com.volia.notificationservice.model.entity.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepo extends CrudRepository<Notification,Long> {
}
