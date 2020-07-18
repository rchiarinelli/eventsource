package com.rchiarinelli.eventsource.coreapi.events.cart;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RemoveCartItemEvent {

    private UUID cartId;

    private String providerId;

    private String serviceId;  
}