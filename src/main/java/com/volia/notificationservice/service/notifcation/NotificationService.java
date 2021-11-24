package com.volia.notificationservice.service.notifcation;

import com.volia.notificationservice.common.exception.LogicalException;
import com.volia.notificationservice.core.common.dto.*;

public interface NotificationService {

    NotifyCustomerResponseDto notifyCustomers(NotifyCustomerRequestDto notifyCustomerRequestDto) throws LogicalException;

    GenericCreationResponse createNotification(CreateNotificationRequestDto createNotificationRequestDto);
}
