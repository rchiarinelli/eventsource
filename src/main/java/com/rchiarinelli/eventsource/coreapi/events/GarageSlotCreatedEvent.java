package com.rchiarinelli.eventsource.coreapi.events;

import java.util.UUID;

import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GarageSlotCreatedEvent {

    private UUID garageSlotId;

    private JsonObject garageSlotData;

    public GarageSlotCreatedEvent(UUID garageSlotId) {
        this.garageSlotId = garageSlotId;
        garageSlotData = new JsonObject();
    }

} 