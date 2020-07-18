package com.rchiarinelli.eventsource.coreapi.commands.cart;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateCartItemCommand {

    @TargetAggregateIdentifier
    private UUID cartId;
    
    private String providerId;

    private String serviceId;

    private Integer quantity;
    
}