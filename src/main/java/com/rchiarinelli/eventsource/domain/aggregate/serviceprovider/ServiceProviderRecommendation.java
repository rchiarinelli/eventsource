package com.rchiarinelli.eventsource.domain.aggregate.serviceprovider;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class ServiceProviderRecommendation {

    @Id
    private String id;

    private Date recomendationDate;

    private String level;

    private String recommendedBy;

}