package com.rchiarinelli.eventsource.coreapi.events;

import java.util.UUID;

import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput;

public class ServiceProviderCreatedEvent {

    private UUID serviceProviderId;

    private ServiceProviderInput data;

    public ServiceProviderCreatedEvent(UUID serviceProviderId, ServiceProviderInput d) {
        this.serviceProviderId = serviceProviderId;
        this.data = d;        
    }

	public UUID getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(UUID serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	public ServiceProviderInput getData() {
		return data;
	}

	public void setData(ServiceProviderInput data) {
		this.data = data;
	}
    
    

}