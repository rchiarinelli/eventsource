package com.rchiarinelli.eventsource.domain.aggregate.serviceprovider;

import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class ServiceProviderAggregate {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String providerId;

    @Column(nullable = false)
    private String providerName;

    @Column(nullable = true)
    private String providerImagePath;

    @Column(nullable = true)
    private Double reviewRate;

    @ElementCollection
    private Map<String, ServiceProviderRecommendation> recommendations;

    @ElementCollection
    private Map<String, ServiceDetails> services;

    public void addRecommendation(String providerId, ServiceProviderRecommendation recommendation) {
        this.recommendations.compute(providerId, (key,value) -> value);
    }

    public void addService(String serviceId, ServiceDetails service) {
        this.services.compute(serviceId, (key, value) -> value);
    }

    public void removeService(String serviceId) {
        this.services.remove(serviceId);
    }

    public void removeRecommendation(String recommendationId) {
        this.recommendations.remove(recommendationId);
    }

}