package com.rchiarinelli.eventsource.service.core;

import java.util.Optional;

import com.rchiarinelli.eventsource.domain.aggregate.Cart;

public interface ServiceRequestService {
    
    Optional<String> addServiceRequest(final Cart cart);


}