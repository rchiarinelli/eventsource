package com.rchiarinelli.eventsource.coreapi.commands.notification;

import java.util.UUID;

import com.rchiarinelli.eventsource.domain.aggregate.ServiceRequestAggregate.ServiceRequestStatusReason;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RejectServiceRequestCommand {

    @TargetAggregateIdentifier
    private UUID serviceRequestAggregateId;

    private ServiceRequestStatusReason reason;
    
}