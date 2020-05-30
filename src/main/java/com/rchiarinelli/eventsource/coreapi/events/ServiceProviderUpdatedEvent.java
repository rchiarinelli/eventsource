package com.rchiarinelli.eventsource.coreapi.events;

import java.util.Optional;
import java.util.UUID;

import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.RecommendationDetailsInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.ServiceDetailsInput;

import lombok.Getter;

public class ServiceProviderUpdatedEvent {

    @Getter
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
        return Optional.of(details);
    }

    public Optional<RecommendationDetailsInput> getRecommendationDetails() {
        return Optional.of(recommendationDetails);
    }

}