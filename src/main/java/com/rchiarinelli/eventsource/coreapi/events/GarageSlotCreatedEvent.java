package com.rchiarinelli.eventsource.coreapi.events;

import java.util.UUID;

public class GarageSlotCreatedEvent {

    private UUID garageSlotId;

    public GarageSlotCreatedEvent(UUID garageSlotId) {
        this.garageSlotId = garageSlotId;
    }

    public UUID getGarageSlotId() {
        return garageSlotId;
    }

    public void setGarageSlotId(UUID garageSlotId) {
        this.garageSlotId = garageSlotId;
    }
    

} 