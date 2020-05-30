package com.rchiarinelli.eventsource.coreapi.commands.create;

import java.util.UUID;

import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput;

import org.axonframework.commandhandling.RoutingKey;

import lombok.Getter;

@Getter
public class CreateServiceProviderCommand {

    @RoutingKey
    private UUID slotId;

    private ServiceProviderInput data;

    public CreateServiceProviderCommand(UUID slotId, ServiceProviderInput d) {
        this.slotId = slotId;
        this.data = d;
    }


}