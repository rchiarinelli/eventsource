package com.rchiarinelli.eventsource.handler;

import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

        final JsonObject garageData = (JsonObject) JsonParser.parseString(command.getGarageData());
        final JsonObject ownerData = (JsonObject) JsonParser.parseString(command.getOwnerData());
        final JsonObject slotData = (JsonObject)  JsonParser.parseString(command.getSlotData());

        final JsonObject garageSlotData = new JsonObject();

        garageSlotData.addProperty("garageUUID", garageData.get("id").getAsString());

        garageSlotData.addProperty("ownerUUID", ownerData.get("id").getAsString());
        garageSlotData.addProperty("ownerName", ownerData.get("name").getAsString());

        garageSlotData.addProperty("periodType", slotData.get("periodType").getAsString());
        garageSlotData.addProperty("minimunTime", slotData.get("minimunTime").getAsDouble());
        garageSlotData.addProperty("maximunTime", slotData.get("maximunTime").getAsDouble());
        garageSlotData.addProperty("billingPeriodType", slotData.get("billingPeriodType").getAsString());
        garageSlotData.addProperty("valuePerPeriod", slotData.get("valuePerPeriod").getAsDouble());
        garageSlotData.addProperty("available", slotData.get("available").getAsBoolean());
        garageSlotData.addProperty("active", slotData.get("active").getAsBoolean());

        garageSlotData.addProperty("address1", garageData.get("address1").getAsString());
        garageSlotData.addProperty("address2", garageData.get("address2").getAsString());
        garageSlotData.addProperty("address3", garageData.get("address3").getAsString());
        garageSlotData.addProperty("city", garageData.get("city").getAsString());
        garageSlotData.addProperty("state", garageData.get("state").getAsString());
        garageSlotData.addProperty("country", garageData.get("country").getAsString());
        garageSlotData.addProperty("zipCode", garageData.get("zipCode").getAsString());
        garageSlotData.addProperty("latitude", garageData.get("latitude").getAsDouble());
        garageSlotData.addProperty("longitude", garageData.get("longitude").getAsDouble());

        AggregateLifecycle.apply(new GarageSlotCreatedEvent(command.getSlotId(), garageSlotData.toString()));
    }


    @EventSourcingHandler
    public void on(GarageSlotCreatedEvent event) {
        log.debug("Garage slot created. " + event.toString());
        this.garageSlotId = event.getGarageSlotId();
    }

    
}