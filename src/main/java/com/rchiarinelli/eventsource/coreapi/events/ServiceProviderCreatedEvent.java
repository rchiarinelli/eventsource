package com.rchiarinelli.eventsource.coreapi.events;

import java.util.UUID;

import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceProviderCreatedEvent {

    private UUID serviceProviderId;

    private ServiceProviderInput data;

    public ServiceProviderCreatedEvent(UUID serviceProviderId, ServiceProviderInput d) {
        this.serviceProviderId = serviceProviderId;
        this.data = d;        
    }

}