package com.rchiarinelli.eventsource.coreapi.commands;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ConfirmGarageReservationCommand {

    @TargetAggregateIdentifier
    private UUID garageSlotId;

    public UUID getGarageSlotId() {
        return garageSlotId;
    }

    public void setGarageSlotId(UUID garageSlotId) {
        this.garageSlotId = garageSlotId;
    }

    

}