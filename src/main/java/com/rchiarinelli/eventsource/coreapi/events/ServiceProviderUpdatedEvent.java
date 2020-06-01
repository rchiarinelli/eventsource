package com.rchiarinelli.eventsource.coreapi.events;

import java.util.Optional;
import java.util.UUID;

import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.RecommendationDetailsInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.ServiceDetailsInput;

public class ServiceProviderUpdatedEvent {

    private UUID serviceProviderId;

    private ServiceDetailsInput details;

    private RecommendationDetailsInput recommendationDetails;

    public ServiceProviderUpdatedEvent(UUID serviceProviderId, ServiceDetailsInput details,
            RecommendationDetailsInput recommendationDetails) {
        this.serviceProviderId = serviceProviderId;
        this.details = details;
        this.recommendationDetails = recommendationDetails;
    }

    public Optional<ServiceDetailsInput> getDetails() {
        return Optional.ofNullable(details);
    }

    public Optional<RecommendationDetailsInput> getRecommendationDetails() {
        return Optional.ofNullable(recommendationDetails);
    }

	public void setServiceProviderId(UUID serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	public void setDetails(ServiceDetailsInput details) {
		this.details = details;
	}

	public void setRecommendationDetails(RecommendationDetailsInput recommendationDetails) {
		this.recommendationDetails = recommendationDetails;
	}

	public UUID getServiceProviderId() {
		return serviceProviderId;
	}
}