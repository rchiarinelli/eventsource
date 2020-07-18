package com.rchiarinelli.eventsource.coreapi.events.cart;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CancelCartEvent {

    private UUID cartId;

}