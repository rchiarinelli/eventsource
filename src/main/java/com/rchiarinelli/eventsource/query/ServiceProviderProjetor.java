package com.rchiarinelli.eventsource.query;

import java.math.BigDecimal;

import com.rchiarinelli.eventsource.coreapi.events.ServiceProviderCreatedEvent;
import com.rchiarinelli.eventsource.coreapi.events.ServiceProviderUpdatedEvent;
import com.rchiarinelli.eventsource.domain.aggregate.serviceprovider.ServiceDetails;
import com.rchiarinelli.eventsource.domain.aggregate.serviceprovider.ServiceProviderAggregate;
import com.rchiarinelli.eventsource.domain.aggregate.serviceprovider.ServiceProviderRecommendation;

import org.apache.commons.lang.StringUtils;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ServiceProviderProjetor {

    private ServiceProviderAggregateRepository repository;

    public ServiceProviderProjetor(ServiceProviderAggregateRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(ServiceProviderCreatedEvent event) {
        log.debug("Saving new ServiceProviderCreatedEvent");
        final var serviceProvider = new ServiceProviderAggregate();

        serviceProvider.setId(event.getServiceProviderId());
        serviceProvider.setProviderId(event.getData().getProviderId());
        serviceProvider.setProviderImagePath(event.getData().getProviderImg());
        serviceProvider.setProviderName(event.getData().getProviderName());
        serviceProvider.setReviewRate(event.getData().getReviewRate());
        
        repository.save(serviceProvider);
    }

    @EventHandler
    public void on(ServiceProviderUpdatedEvent event) {
        log.debug("Received ServiceProviderUpdatedEvent");

        final var serviceProvider = repository.findById(event.getServiceProviderId());

        if (serviceProvider.isPresent()) {
            log.debug("Updating aggregate.");

            event.getDetails().ifPresent(detail -> {
                log.debug("Adding service detail.");
                serviceProvider.get().addService(detail.getId(), new ServiceDetails(detail.getId(), detail.getServiceName(), StringUtils.EMPTY, StringUtils.EMPTY, BigDecimal.valueOf(0D), 0D, 0));

            });
            event.getRecommendationDetails().ifPresent(recommendation -> {
                log.debug("Adding recommendation.");
                serviceProvider.get().addRecommendation(recommendation.getId(), new ServiceProviderRecommendation(recommendation.getId(),recommendation.getLevel(),recommendation.getRecommendedBy()));
            } );
        
            repository.save(serviceProvider.get());
        }
    }


}