package com.rchiarinelli.eventsource.projectors;

import java.util.Optional;

import com.rchiarinelli.eventsource.coreapi.events.servicerequest.AcceptServiceRequestEvent;
import com.rchiarinelli.eventsource.coreapi.events.servicerequest.CreateServiceRequestEvent;
import com.rchiarinelli.eventsource.coreapi.events.servicerequest.RejectServiceRequestEvent;
import com.rchiarinelli.eventsource.domain.aggregate.Cart;
import com.rchiarinelli.eventsource.domain.aggregate.ServiceRequestAggregate;
import com.rchiarinelli.eventsource.domain.repositories.CartRepository;
import com.rchiarinelli.eventsource.domain.repositories.ServiceRequestAggregateRepository;
import com.rchiarinelli.eventsource.service.core.ServiceRequestService;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ServiceRequestProjector {

    private ServiceRequestAggregateRepository repository;

    private CartRepository cartRepository;

    private ServiceRequestService serviceRequestService;

    public ServiceRequestProjector(ServiceRequestAggregateRepository repository, CartRepository cartRepository,
            ServiceRequestService serviceRequestService) {
        this.repository = repository;
        this.cartRepository = cartRepository;
        this.serviceRequestService = serviceRequestService;
    }

    @EventHandler
    public void on(final CreateServiceRequestEvent openEvent) {
        log.debug("Creating service request");

        Optional<Cart> cartOp = cartRepository.findById(openEvent.getCartId()).filter(cart -> cart.isConfirmed());

        if (cartOp.isPresent()) {
            serviceRequestService.addServiceRequest(cartOp.get())
                    .ifPresent(serviceId -> repository.save(ServiceRequestAggregate.builder()
                            .serviceRequestAggregateId(openEvent.getServiceRequestAggregateId())
                            .cartId(cartOp.get().getId()).serviceRequestId(serviceId).build()));
            repository.save(ServiceRequestAggregate.builder()
                    .serviceRequestAggregateId(openEvent.getServiceRequestAggregateId()).cartId(openEvent.getCartId())
                    .build());
        }
        repository.flush();
    }

    @EventHandler
    public void on(final RejectServiceRequestEvent rejectEvent) {
        log.debug("Rejecting service request");
        repository.findById(rejectEvent.getServiceRequestAggregateId())
                .filter(serviceRequest -> serviceRequest.isPending()).ifPresent(serviceRequest -> {
                    serviceRequest.reject(rejectEvent.getReason());
                    repository.save(serviceRequest);
                });
        repository.flush();
    }

    @EventHandler
    public void on(final AcceptServiceRequestEvent acceptEvent) {
        log.debug("Accepting service request");
        repository.findById(acceptEvent.getServiceRequestAggregateId())
                .filter(serviceRequest -> serviceRequest.isPending()).ifPresent(serviceRequest -> {
                    serviceRequest.accept();
                    repository.save(serviceRequest);
                });
        repository.flush();
    }

}