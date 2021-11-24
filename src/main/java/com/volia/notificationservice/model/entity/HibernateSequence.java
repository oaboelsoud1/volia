package com.volia.notificationservice.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//this entity stores the last id record to

@Data
@ToString(of = {"id", "sequenceName", "sequenceValue"})
@Entity
@Table(name = "notification_hibernate_sequence")
@EqualsAndHashCode(of = "id", callSuper = false)
public class HibernateSequence implements Serializable {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "sequence_name")
    private String sequenceName;

    @Column(name = "sequence_value")
    private long sequenceValue;
}
