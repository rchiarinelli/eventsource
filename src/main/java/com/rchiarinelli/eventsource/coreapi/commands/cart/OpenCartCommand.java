package com.rchiarinelli.eventsource.coreapi.commands.cart;

import java.util.UUID;

import org.axonframework.commandhandling.RoutingKey;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpenCartCommand {

    @RoutingKey
    private UUID cartId;

    private String customerId;
    
}