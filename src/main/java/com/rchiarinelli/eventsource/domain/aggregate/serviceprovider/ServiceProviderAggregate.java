package com.rchiarinelli.eventsource.domain.aggregate.serviceprovider;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
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
    private double reviewRate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ServiceProviderRecommendation> recommendations;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ServiceDetails> services;

    public void addRecommendation(String providerId, ServiceProviderRecommendation recommendation) {
    	if (getRecommendations() == null) {
    		recommendations = new HashSet<>();
    	}
        if (!this.recommendations.contains(recommendation) && !this.recommendations.stream().filter(rec -> rec.getId().equals(recommendation.getId())).findAny().isPresent()) {
            this.recommendations.add(recommendation);
        }
    }

    public void addService(String serviceId, ServiceDetails service) {
    	if (getServices() == null) {
    		services = new HashSet<>();
    	}
    	if (!services.contains(service) && !services.stream().filter(serv -> serv.getId().equals(service.getId())).findAny().isPresent()) {
            services.add(service);
        }
    }
}
