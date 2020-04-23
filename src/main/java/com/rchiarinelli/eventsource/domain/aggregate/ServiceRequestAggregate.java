package com.rchiarinelli.eventsource.domain.aggregate;

import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.rchiarinelli.eventsource.domain.aggregate.serviceprovider.ServiceDetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class ServiceRequestAggregate {

    @Id
    private UUID id;

    @Column
    private UUID serviceProviderAggregateId;

    @ElementCollection
    private Map<String, ServiceDetails> services;


    public void addService(String serviceId, ServiceDetails service) {
        this.services.compute(serviceId, (key, value) -> value);
    }

    public void removeService(String serviceId) {
        this.services.remove(serviceId);
    }

}