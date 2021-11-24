package com.volia.notificationservice.service.notifcation;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import com.volia.notificationservice.common.exception.LogicalException;
import com.volia.notificationservice.common.exception.ServerError;
import com.volia.notificationservice.core.common.JpaPagingUtil;
import com.volia.notificationservice.core.common.dto.*;
import com.volia.notificationservice.model.entity.Notification;
import com.volia.notificationservice.model.entity.User;
import com.volia.notificationservice.model.entity.UserNotificationLog;
import com.volia.notificationservice.model.repository.HibernateSequenceRepo;
import com.volia.notificationservice.model.repository.NotificationRepo;
import com.volia.notificationservice.model.repository.UserNotificationLogRepo;
import com.volia.notificationservice.service.User.UserService;
import com.volia.notificationservice.service.provider.NotificationServiceProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final static Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final UserService userService;
    private final UserNotificationLogRepo userNotificationLogRepo;
    private final HibernateSequenceRepo hibernateSequenceRepo;
    private final NotificationRepo notificationRepo;

    @Autowired
    public NotificationServiceImpl(UserService userService,
                                   UserNotificationLogRepo userNotificationLogRepo,
                                   HibernateSequenceRepo hibernateSequenceRepo,
                                   NotificationRepo notificationRepo) {
        this.userService = userService;
        this.userNotificationLogRepo = userNotificationLogRepo;
        this.hibernateSequenceRepo = hibernateSequenceRepo;
        this.notificationRepo = notificationRepo;

    }

    @Override
    public NotifyCustomerResponseDto notifyCustomers(NotifyCustomerRequestDto notifyCustomerRequestDto) throws LogicalException {
        try {

            // pagination is not a good practice for loading data batch . you should use queue as well to avoid skipping elements
            Integer size = 100;
            Integer page = 1;
            Pageable pageable = JpaPagingUtil.getPage(page, size);
            Page<User> userPage = userService.getUsers(notifyCustomerRequestDto.getCityName(), notifyCustomerRequestDto.getUserNameFirstLetter(), pageable);
            // not a good use to use premative datatypes . they doesn't handle null values bs yala mafesh w2at :D
            int TotalNumberOfUsersHasBeenNotified = 0;
            while (userPage.hasNext()) {
                List<UserNotificationLog> recipientList = new ArrayList<>();
                userPage.getContent().stream().forEach(customer ->
                {
                    NotificationServiceProviderFactory.getInstance(customer.getNotificationType())
                            .sendNotification(customer.getName(), notifyCustomerRequestDto.getNotificationCode());
                    // should be a flag .isSuccess before add to recipientList
                    recipientList.add(UserNotificationLog.builder().userId(customer.getNotificationLog().getUserId()).notificationType(customer.getNotificationType()).notificationCode(notifyCustomerRequestDto.getNotificationCode()).build());
                });

                userNotificationLogRepo.saveAll(recipientList);
                TotalNumberOfUsersHasBeenNotified += recipientList.size();
                pageable = userPage.nextPageable();
                userPage = userService.getUsers(notifyCustomerRequestDto.getCityName(), notifyCustomerRequestDto.getUserNameFirstLetter(), pageable);

            }
            return NotifyCustomerResponseDto.builder().totalNumberOfNotifiedCustomer(TotalNumberOfUsersHasBeenNotified).build();

        } catch (LogicalException e) {
            logger.error().exception("Logical Error happened while notify Customers", e)
                    .field("cityCode", notifyCustomerRequestDto.getCityName())
                    .field("userNameFirstLetter", notifyCustomerRequestDto.getUserNameFirstLetter())
                    .log();
            throw e;

        } catch (Exception e) {
            logger.error().exception("Logical Error happened while notify Customers", e)
                    .field("cityCode", notifyCustomerRequestDto.getCityName())
                    .field("userNameFirstLetter", notifyCustomerRequestDto.getUserNameFirstLetter())
                    .log();
            throw new LogicalException(ServerError.NOTIFY_CUSTOMER_FAILED);
        }
    }

    @Override
    public GenericCreationResponse createNotification(CreateNotificationRequestDto createNotificationRequestDto) {
        try {
            Notification notification = Notification.builder().text(createNotificationRequestDto.getText()).code(generateNewNotificationCode()).build();
            notificationRepo.save(notification);
            logger.info().message("New Notification has been created successfully").
                    field("text", createNotificationRequestDto.getText())
                    .log();
            GenericCreationResponse genericCreationResponse = new GenericCreationResponse();
            genericCreationResponse.setContent(createNotificationRequestDto.getText());
            genericCreationResponse.setCode(notification.getCode());
            return genericCreationResponse;
        } catch (LogicalException e) {
            logger.error().exception("Logical Error happened while creating new notification", e)
                    .field("text", createNotificationRequestDto.getText())
                    .log();
            throw e;

        } catch (Exception e) {
            logger.error().exception("Logical Error happened while creating new notification", e)
                    .field("text", createNotificationRequestDto.getText())
                    .log();
            throw new LogicalException(ServerError.NOTIFY_CUSTOMER_FAILED);
        }
    }

    private String generateNewNotificationCode() {
        return String.format("NT-%s", this.hibernateSequenceRepo.doGetNotificationSequenceNextValue("notification_code_sequence"));
    }
}
