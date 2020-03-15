package com.rchiarinelli.eventsource.coreapi.commands;

import java.util.UUID;

import org.axonframework.commandhandling.RoutingKey;

public class CreateGarageSlotCommand {

    @RoutingKey
    private UUID slotId;

    

    public UUID getSlotId() {
        return slotId;
    }

    public void setSlotId(UUID slotId) {
        this.slotId = slotId;
    }

    public CreateGarageSlotCommand(UUID slotId) {
        this.slotId = slotId;
    }

    



}