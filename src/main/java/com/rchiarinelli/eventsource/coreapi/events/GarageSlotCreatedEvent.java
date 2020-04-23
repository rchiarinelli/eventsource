package com.rchiarinelli.eventsource.coreapi.events;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GarageSlotCreatedEvent {

    private UUID garageSlotId;

    private String garageSlotData;

    public GarageSlotCreatedEvent(UUID garageSlotId, String garageSlotData) {
        this.garageSlotId = garageSlotId;
        this.garageSlotData = garageSlotData;
    }

} 