package com.rchiarinelli.eventsource.coreapi.commands;

import java.util.UUID;

import com.google.gson.JsonObject;

import org.axonframework.commandhandling.RoutingKey;

import lombok.Getter;

@Getter
public class CreateGarageSlotCommand {

    @RoutingKey
    private UUID slotId;

    private JsonObject garageData;

    private JsonObject ownerData;

    private JsonObject slotData;

    public CreateGarageSlotCommand(UUID slotId, JsonObject garageData, JsonObject ownerData, JsonObject slotData) {
        this.slotId = slotId;
        this.garageData = garageData;
        this.ownerData = ownerData;
        this.slotData = slotData;
    }

}