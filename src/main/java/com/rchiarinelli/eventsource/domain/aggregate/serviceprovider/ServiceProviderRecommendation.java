package com.rchiarinelli.eventsource.domain.aggregate.serviceprovider;

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

    private String level;

    private String recommendedBy;

    public ServiceProviderRecommendation() {
    }

    public ServiceProviderRecommendation(String id, String level, String recommendedBy) {
        this.id = id;
        this.level = level;
        this.recommendedBy = recommendedBy;
    }

    
    

}