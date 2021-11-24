package com.volia.notificationservice.model.entity;

import com.volia.notificationservice.common.AbstractEntity;
import com.volia.notificationservice.model.entity.converter.NotificationTypeConverter;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = City.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Convert(converter = NotificationTypeConverter.class)
    @Column(name = "notification_preference", length = 50)
    private NotificationType notificationType;

    @OneToOne(targetEntity = UserNotificationLog.class, mappedBy = "userId", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private UserNotificationLog notificationLog;

}
