package com.rchiarinelli.eventsource.handler;

import java.util.UUID;

import com.rchiarinelli.eventsource.coreapi.events.ServiceProviderCreatedEvent;
import com.rchiarinelli.eventsource.coreapi.events.ServiceProviderUpdatedEvent;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.RecommendationDetailsInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.ServiceDetailsInput;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.RoutingKey;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.extern.log4j.Log4j2;

@Aggregate
@Log4j2
public class ServiceProviderHandler {

    @AggregateIdentifier
    private UUID serviceProviderId;

    public ServiceProviderHandler() {}

    @CommandHandler
    public ServiceProviderHandler(CreateServiceProviderCommand command) {
        log.debug("Received CreateServiceProviderCommand. Creating ServiceProviderCreatedEvent event.");
        AggregateLifecycle.apply(new ServiceProviderCreatedEvent(command.getServiceProviderId(), command.getData()));

    }

    @CommandHandler
    public void handle(UpdateServiceProviderCommand command) {
        log.debug("Received UpdateServiceProviderCommand. Creating ServiceProviderUpdatedEvent event.");
        AggregateLifecycle.apply(new ServiceProviderUpdatedEvent(serviceProviderId, command.getDetails(), command.getRecommendationDetails()));
    }

    @EventSourcingHandler
    public void on(ServiceProviderCreatedEvent event) {
        log.debug("Service provider created. " + event.toString());
        serviceProviderId = event.getServiceProviderId();
    }

    public static class CreateServiceProviderCommand {
    
        @RoutingKey
        private UUID serviceProviderId;
    
        private ServiceProviderInput data;
    
        public CreateServiceProviderCommand(UUID serviceProviderId, ServiceProviderInput d) {
            this.serviceProviderId = serviceProviderId;
            this.data = d;
        }

        public UUID getServiceProviderId() {
            return serviceProviderId;
        }

        public void setServiceProviderId(UUID serviceProviderId) {
            this.serviceProviderId = serviceProviderId;
        }

        public ServiceProviderInput getData() {
            return data;
        }

        public void setData(ServiceProviderInput data) {
            this.data = data;
        }

        
    
    
    }

    public static class UpdateServiceProviderCommand {

        @TargetAggregateIdentifier
        private UUID serviceProviderId;
    
        private ServiceDetailsInput details;
    
        private RecommendationDetailsInput recommendationDetails;
    
        public UpdateServiceProviderCommand(UUID serviceProviderId, ServiceDetailsInput details,
                RecommendationDetailsInput recommendationDetails) {
            this.serviceProviderId = serviceProviderId;
            this.details = details;
            this.recommendationDetails = recommendationDetails;
        }
    
        public UUID getServiceProviderId() {
            return serviceProviderId;
        }
    
        public void setServiceProviderId(UUID serviceProviderId) {
            this.serviceProviderId = serviceProviderId;
        }
    
        public ServiceDetailsInput getDetails() {
            return details;
        }
    
        public void setDetails(ServiceDetailsInput details) {
            this.details = details;
        }
    
        public RecommendationDetailsInput getRecommendationDetails() {
            return recommendationDetails;
        }
    
        public void setRecommendationDetails(RecommendationDetailsInput recommendationDetails) {
            this.recommendationDetails = recommendationDetails;
        }
    
    }
    

}