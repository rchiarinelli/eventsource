package com.rchiarinelli.eventsource.handler;

import java.util.UUID;

import com.rchiarinelli.eventsource.coreapi.commands.create.CreateServiceProviderCommand;
import com.rchiarinelli.eventsource.coreapi.commands.update.UpdateServiceProviderCommand;
import com.rchiarinelli.eventsource.coreapi.events.ServiceProviderCreatedEvent;
import com.rchiarinelli.eventsource.coreapi.events.ServiceProviderUpdatedEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.extern.log4j.Log4j2;

@Aggregate
@Log4j2
public class ServiceProviderHandler {

    @AggregateIdentifier
    private UUID serviceProviderId;

    @CommandHandler
    public ServiceProviderHandler(CreateServiceProviderCommand command) {
        log.debug("Received CreateServiceProviderCommand. Creating ServiceProviderCreatedEvent event.");
        serviceProviderId = command.getSlotId();
        AggregateLifecycle.apply(new ServiceProviderCreatedEvent(command.getSlotId(), command.getData()));

    }

    @CommandHandler
    public ServiceProviderHandler(UpdateServiceProviderCommand command) {
        log.debug("Received UpdateServiceProviderCommand. Creating ServiceProviderUpdatedEvent event.");
        serviceProviderId = command.getSlotId();
        AggregateLifecycle.apply(new ServiceProviderUpdatedEvent(command.getSlotId(), command.getDetails(), command.getRecommendationDetails()));
    }

    @EventSourcingHandler
    public void on(ServiceProviderCreatedEvent event) {
        log.debug("Service provider created. " + event.toString());
        serviceProviderId = event.getServiceProviderId();
    }

}