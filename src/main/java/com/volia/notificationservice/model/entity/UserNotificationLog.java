package com.volia.notificationservice.model.entity;

import com.volia.notificationservice.common.AbstractEntity;
import com.volia.notificationservice.model.entity.converter.NotificationTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_notification_log")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserNotificationLog extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    Long id;

    @OneToOne(targetEntity = User.class , fetch = FetchType.LAZY)
    @Column(name = "user_Id")
    private User userId;

    @Column(name = "notification_code" , length = 50)
    private String notificationCode;

    @Column(name = "notification_preference")
    @Convert(converter = NotificationTypeConverter.class )
    private NotificationType notificationType;


}
