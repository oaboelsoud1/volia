package com.volia.notificationservice.model.entity;

import com.volia.notificationservice.common.AbstractEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name= "city")
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class City extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    private Long id ;


    @Column(name = "name",unique = true, nullable = false)
    private String name ;
}
