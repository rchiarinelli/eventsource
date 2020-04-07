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

        GarageSlotCreatedEvent event = new GarageSlotCreatedEvent(command.getSlotId());

        event.getGarageSlotData().addProperty("garageUUID", command.getGarageData().get("id").getAsString());

        event.getGarageSlotData().addProperty("ownerUUID", command.getOwnerData().get("id").getAsString());
        event.getGarageSlotData().addProperty("ownerName", command.getOwnerData().get("name").getAsString());

        event.getGarageSlotData().addProperty("periodType", command.getSlotData().get("periodType").getAsString());
        event.getGarageSlotData().addProperty("minimunTime", command.getSlotData().get("minimunTime").getAsDouble());
        event.getGarageSlotData().addProperty("maximunTime", command.getSlotData().get("maximunTime").getAsDouble());
        event.getGarageSlotData().addProperty("billingPeriodType", command.getSlotData().get("billingPeriodType").getAsString());
        event.getGarageSlotData().addProperty("valuePerPeriod", command.getSlotData().get("valuePerPeriod").getAsDouble());
        event.getGarageSlotData().addProperty("available", command.getSlotData().get("available").getAsBoolean());
        event.getGarageSlotData().addProperty("active", command.getSlotData().get("active").getAsBoolean());

        event.getGarageSlotData().addProperty("address1", command.getGarageData().get("address1").getAsString());
        event.getGarageSlotData().addProperty("address2", command.getGarageData().get("address2").getAsString());
        event.getGarageSlotData().addProperty("address3", command.getGarageData().get("address3").getAsString());
        event.getGarageSlotData().addProperty("city", command.getGarageData().get("city").getAsString());
        event.getGarageSlotData().addProperty("state", command.getGarageData().get("state").getAsString());
        event.getGarageSlotData().addProperty("country", command.getGarageData().get("country").getAsString());
        event.getGarageSlotData().addProperty("zipCode", command.getGarageData().get("zipCode").getAsString());
        event.getGarageSlotData().addProperty("latitude", command.getGarageData().get("latitude").getAsDouble());
        event.getGarageSlotData().addProperty("longitude", command.getGarageData().get("longitude").getAsDouble());


        AggregateLifecycle.apply(new GarageSlotCreatedEvent(command.getSlotId()));
    }


    @EventSourcingHandler
    public void on(GarageSlotCreatedEvent event) {
        log.debug("Garage slot created. " + event.toString());
        this.garageSlotId = event.getGarageSlotId();
    }

    
}