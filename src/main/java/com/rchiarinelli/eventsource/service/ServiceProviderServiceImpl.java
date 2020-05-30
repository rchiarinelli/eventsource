package com.rchiarinelli.eventsource.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.rchiarinelli.eventsource.coreapi.commands.create.CreateServiceProviderCommand;
import com.rchiarinelli.eventsource.coreapi.commands.update.UpdateServiceProviderCommand;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.RecommendationDetailsInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.ServiceDetailsInput;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.ExecutionException;
import org.axonframework.queryhandling.QueryGateway;
import org.hibernate.type.UUIDBinaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private CommandGateway commandGateway;

    private QueryGateway queryGateway;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gig.core.url}")
    private String endpointUrl;

    public ServiceProviderServiceImpl(final CommandGateway commandGateway, final QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @Override
    public UUID createServiceProviderAggregate(ServiceProviderInput input) {
        log.debug("Sending message to Axon Gateway. CreateServiceProviderCommand.");
        final CompletableFuture<UUID> result = commandGateway
                .send(new CreateServiceProviderCommand(UUID.randomUUID(), input));

        log.debug("Command sent");
        try {
            log.debug("Retrieving command result");
            return result.get();
        } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
            log.error(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean addServiceToProvider(UUID serviceProviderId, ServiceDetailsInput serviceDetails) {
        log.debug("Sending message to Axon Gateway. UpdateServiceProviderCommand");
        commandGateway.send(new UpdateServiceProviderCommand(serviceProviderId, serviceDetails, null));
        return Boolean.TRUE;
    }

    @Override
    public Boolean addRecommendationToProvider(UUID serviceProviderId,
            RecommendationDetailsInput recommendationDetail) {
        log.debug("Sending message to Axon Gateway. UpdateServiceProviderCommand");
        commandGateway.send(new UpdateServiceProviderCommand(serviceProviderId, null, recommendationDetail));
        return Boolean.TRUE;
    }

}