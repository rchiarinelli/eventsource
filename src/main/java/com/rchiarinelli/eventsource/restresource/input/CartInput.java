package com.rchiarinelli.eventsource.restresource.input;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartInput {

    @NotNull
    @NotEmpty
    private String customerId;

    private String cartId;

    private String providerId;

    private String serviceId;

    private Integer quantity;
}