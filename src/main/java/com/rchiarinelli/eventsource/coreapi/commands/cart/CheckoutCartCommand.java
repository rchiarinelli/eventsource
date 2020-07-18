package com.rchiarinelli.eventsource.coreapi.commands.cart;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CheckoutCartCommand {
    
    @TargetAggregateIdentifier
    private UUID cartId;
}