package com.rchiarinelli.eventsource.coreapi.commands;

import java.util.UUID;

import com.google.gson.JsonObject;

import org.axonframework.commandhandling.RoutingKey;

import lombok.Getter;

@Getter
public class CreateGarageSlotCommand {

    @RoutingKey
    private UUID slotId;

    private String garageData;

    private String ownerData;

    private String slotData;

    public CreateGarageSlotCommand(UUID slotId, String garageData, String ownerData, String slotData) {
        this.slotId = slotId;
        this.garageData = garageData;
        this.ownerData = ownerData;
        this.slotData = slotData;
    }

}