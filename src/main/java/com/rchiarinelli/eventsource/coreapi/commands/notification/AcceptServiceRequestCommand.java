package com.rchiarinelli.eventsource.coreapi.commands.notification;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AcceptServiceRequestCommand {
    
    @TargetAggregateIdentifier
    private UUID serviceRequestAggregateId;
}


