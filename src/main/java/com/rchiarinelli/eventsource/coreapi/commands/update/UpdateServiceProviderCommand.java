package com.rchiarinelli.eventsource.coreapi.commands.update;

import java.util.Optional;
import java.util.UUID;

import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.RecommendationDetailsInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.ServiceDetailsInput;

import org.axonframework.commandhandling.RoutingKey;

import lombok.Getter;

@Getter
public class UpdateServiceProviderCommand {

    @RoutingKey
    private UUID slotId;

    private ServiceDetailsInput details;

    private RecommendationDetailsInput recommendationDetails;

    public UpdateServiceProviderCommand(UUID slotId, ServiceDetailsInput details,
            RecommendationDetailsInput recommendationDetails) {
        this.slotId = slotId;
        this.details = details;
        this.recommendationDetails = recommendationDetails;
    }


}