package com.rchiarinelli.eventsource.handler;

import java.util.UUID;

import com.rchiarinelli.eventsource.coreapi.commands.CreateGarageSlotCommand;
import com.rchiarinelli.eventsource.coreapi.events.GarageSlotCreatedEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.extern.log4j.Log4j2;

@Aggregate
@Log4j2
public class GarageHandler {


    @AggregateIdentifier
    private UUID garageSlotId;
    
    public GarageHandler() {
        log.debug("Instantiating new garage handler." + this.getClass().hashCode());
    }

    @CommandHandler
    public GarageHandler(CreateGarageSlotCommand command) {
        log.debug("Instantiating new garage handler with commmand." + this.getClass().hashCode());
        AggregateLifecycle.apply(new GarageSlotCreatedEvent(command.getSlotId()));
    }


    @EventSourcingHandler
    public void on(GarageSlotCreatedEvent event) {
        log.debug("Garage slot created. " + event.toString());
        this.garageSlotId = event.getGarageSlotId();
    }

}