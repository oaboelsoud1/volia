package com.volia.notificationservice.model.repository;

import com.volia.notificationservice.model.entity.HibernateSequence;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface HibernateSequenceRepo extends CrudRepository<HibernateSequence,Long> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Procedure("doGetNotificationSequenceNextValue")
    long doGetNotificationSequenceNextValue(String sequence);
}
