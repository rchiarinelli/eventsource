package com.rchiarinelli.eventsource.handler;

import java.util.UUID;

import com.rchiarinelli.eventsource.coreapi.commands.cart.CancelCartCommand;
import com.rchiarinelli.eventsource.coreapi.commands.cart.CheckoutCartCommand;
import com.rchiarinelli.eventsource.coreapi.commands.cart.OpenCartCommand;
import com.rchiarinelli.eventsource.coreapi.commands.cart.RemoveCartItemCommand;
import com.rchiarinelli.eventsource.coreapi.commands.cart.UpdateCartItemCommand;
import com.rchiarinelli.eventsource.coreapi.events.cart.CancelCartEvent;
import com.rchiarinelli.eventsource.coreapi.events.cart.CheckoutCartEvent;
import com.rchiarinelli.eventsource.coreapi.events.cart.OpenCartEvent;
import com.rchiarinelli.eventsource.coreapi.events.cart.RemoveCartItemEvent;
import com.rchiarinelli.eventsource.coreapi.events.cart.UpdateCartItem;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.extern.log4j.Log4j2;

@Aggregate
@Log4j2
public class CartHander {
    
    @AggregateIdentifier
    private UUID cartId;

    public CartHander() {}

    @CommandHandler
    public CartHander(OpenCartCommand command) {
        log.debug("Received OpenCartCommand. Creating OpenCartEvent event.");
        AggregateLifecycle.apply(OpenCartEvent.builder().id(command.getCartId()).customerId(command.getCustomerId()).build());
    }

    @EventSourcingHandler
    public void on(OpenCartEvent event) {
        log.debug("Cart opened. " + event.toString());
        cartId = event.getId();
    }

    @CommandHandler
    public void handle(CancelCartCommand command) {
        log.debug("Received CancelCartCommand.");
        AggregateLifecycle.apply(CancelCartEvent.builder().cartId(cartId).build());
    }

    @CommandHandler
    public void handle(CheckoutCartCommand command) {
        log.debug("Received CheckoutCartCommand.");
        AggregateLifecycle.apply(CheckoutCartEvent.builder().cartId(cartId).build());
    }

    @CommandHandler
    public void handle(RemoveCartItemCommand command) {
        log.debug("Received RemoveCartItemCommand.");
        AggregateLifecycle.apply(RemoveCartItemEvent.builder().cartId(cartId).providerId(command.getProviderId()).serviceId(command.getServiceId()).build());
    }
    @CommandHandler
    public void handle(UpdateCartItemCommand command) {
        log.debug("Received UpdateCartItemCommand.");
        AggregateLifecycle.apply(UpdateCartItem.builder().cartId(cartId).providerId(command.getProviderId()).serviceId(command.getServiceId()).quantity(command.getQuantity()).build());
    }
}