package com.rchiarinelli.eventsource.coreapi.events.servicerequest;

import java.util.UUID;

import com.rchiarinelli.eventsource.domain.aggregate.ServiceRequestAggregate.ServiceRequestStatusReason;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RejectServiceRequestEvent {

    private UUID serviceRequestAggregateId;

    private ServiceRequestStatusReason reason;
    
}