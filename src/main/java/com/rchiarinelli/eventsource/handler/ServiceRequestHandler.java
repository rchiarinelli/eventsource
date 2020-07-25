package com.rchiarinelli.eventsource.handler;

import java.util.UUID;

import com.rchiarinelli.eventsource.coreapi.commands.notification.AcceptServiceRequestCommand;
import com.rchiarinelli.eventsource.coreapi.commands.notification.CreateServiceRequestCommand;
import com.rchiarinelli.eventsource.coreapi.commands.notification.RejectServiceRequestCommand;
import com.rchiarinelli.eventsource.coreapi.events.servicerequest.AcceptServiceRequestEvent;
import com.rchiarinelli.eventsource.coreapi.events.servicerequest.CreateServiceRequestEvent;
import com.rchiarinelli.eventsource.coreapi.events.servicerequest.RejectServiceRequestEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.extern.log4j.Log4j2;

@Aggregate
@Log4j2
public class ServiceRequestHandler {

    @AggregateIdentifier
    private UUID serviecRequestAggregateId;

    public ServiceRequestHandler() {}

    @CommandHandler
    public ServiceRequestHandler(CreateServiceRequestCommand command) {
        log.debug("Received CreateServiceRequestCommand. Creating CreateServiceRequestEvent event.");
        AggregateLifecycle.apply(CreateServiceRequestEvent.builder().serviceRequestAggregateId(UUID.randomUUID()).cartId(command.getCartId()).build());
    }

    @CommandHandler
    public void on(RejectServiceRequestCommand rejectCommand) {
        log.debug("Received RejectServiceRequestCommand. Creating RejectServiceRequestEvent event.");
        AggregateLifecycle.apply(RejectServiceRequestEvent.builder().serviceRequestAggregateId(this.serviecRequestAggregateId).reason(rejectCommand.getReason()).build());
    }

    @CommandHandler
    public void on(AcceptServiceRequestCommand acceptCommand) {
        log.debug("Received AcceptServiceRequestCommand. Creating CreateServiceRequestEvent event.");
        AggregateLifecycle.apply(AcceptServiceRequestEvent.builder().serviceRequestAggregateId(this.serviecRequestAggregateId).build());
    }

    @EventSourcingHandler
    public void on(CreateServiceRequestEvent event) {
        log.debug("Service Request created. " + event.toString());
        serviecRequestAggregateId = event.getServiceRequestAggregateId();
    }   
}