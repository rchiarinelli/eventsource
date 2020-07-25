package com.rchiarinelli.eventsource.coreapi.commands.notification;

import java.util.UUID;

import org.axonframework.commandhandling.RoutingKey;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateServiceRequestCommand {

    @RoutingKey
    private UUID serviceRequestAggregateId;

    private UUID cartId;
}




   