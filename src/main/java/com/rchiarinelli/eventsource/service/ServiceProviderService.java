package com.rchiarinelli.eventsource.service;

import java.util.UUID;

import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.RecommendationDetailsInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.ServiceDetailsInput;

public interface ServiceProviderService {

    UUID createServiceProviderAggregate(final ServiceProviderInput input);

    Boolean addServiceToProvider(final UUID serviceProviderId, final ServiceDetailsInput serviceDetails);

    Boolean addRecommendationToProvider(final UUID serviceProviderId, final RecommendationDetailsInput recommendationDetail);

}