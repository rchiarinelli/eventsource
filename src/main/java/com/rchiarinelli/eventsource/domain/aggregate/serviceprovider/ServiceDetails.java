package com.rchiarinelli.eventsource.domain.aggregate.serviceprovider;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class ServiceDetails {

    @Id
    private String id;

    private String name;

    private String description;

    private String serviceProviderId;

    private String serviceType;

    private BigDecimal value;

    private double minimunCancelationValue;

    private int cancelationCriteria;
}