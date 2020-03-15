package com.rchiarinelli.eventsource.coreapi.events;

import java.util.UUID;

public class GarageSlotReservedEvent {

    
    private UUID garageSlotId;

    public UUID getGarageSlotId() {
        return garageSlotId;
    }

    public void setGarageSlotId(UUID garageSlotId) {
        this.garageSlotId = garageSlotId;
    }


}