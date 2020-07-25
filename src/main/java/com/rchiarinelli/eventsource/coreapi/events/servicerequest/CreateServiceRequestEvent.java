package com.rchiarinelli.eventsource.coreapi.events.servicerequest;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateServiceRequestEvent {

    private UUID serviceRequestAggregateId;

    private UUID cartId;
    
}