package com.rchiarinelli.eventsource.projectors;

import com.rchiarinelli.eventsource.coreapi.events.cart.AddCartItemEvent;
import com.rchiarinelli.eventsource.coreapi.events.cart.CancelCartEvent;
import com.rchiarinelli.eventsource.coreapi.events.cart.CheckoutCartEvent;
import com.rchiarinelli.eventsource.coreapi.events.cart.OpenCartEvent;
import com.rchiarinelli.eventsource.coreapi.events.cart.RemoveCartItemEvent;
import com.rchiarinelli.eventsource.coreapi.events.cart.UpdateCartItemEvent;
import com.rchiarinelli.eventsource.domain.aggregate.Cart;
import com.rchiarinelli.eventsource.domain.repositories.CartRepository;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CartProjector {

    private CartRepository repository;

    public CartProjector(CartRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(OpenCartEvent openEvent) {
        log.debug("Opening a new cart");
        Cart cart = Cart.builder().customerId(openEvent.getCustomerId()).id(openEvent.getId()).build();
        repository.save(cart);
    }

    @EventHandler
    public void on(AddCartItemEvent addEvent) {
        repository.findById(addEvent.getCartId()).filter(f -> f.isOpen()).ifPresent(cart -> {
            log.debug("Adding item to cart");
            cart.updateServiceItem(addEvent.getProviderId(), addEvent.getServiceId(), addEvent.getQuantity());
            repository.save(cart);
        });
    }

    @EventHandler
    public void on(UpdateCartItemEvent updateEvent) {
        repository.findById(updateEvent.getCartId()).filter(f -> f.isOpen()).ifPresent(cart -> {
            log.debug("Updating cart item");
            cart.updateServiceItem(updateEvent.getProviderId(), updateEvent.getServiceId(), updateEvent.getQuantity());
            repository.save(cart);
        });
    }

    @EventHandler
    public void on(RemoveCartItemEvent removeEvent) {
        repository.findById(removeEvent.getCartId()).filter(f -> f.isOpen()).ifPresent(cart -> {
            log.debug("Removing item from cart");
            cart.removeServiceItem(removeEvent.getProviderId(), removeEvent.getServiceId());
            repository.save(cart);
        });
    }

    @EventHandler
    public void on(CancelCartEvent cancelCartEvent) {
        repository.findById(cancelCartEvent.getCartId()).filter(f -> f.isOpen()).ifPresent(cart -> {
            log.debug("Canceling cart");
            cart.cancel();
            repository.save(cart);
        });
    }

    @EventHandler
    public void on(CheckoutCartEvent confirmEvent) {
        log.debug("Confirming cart checkout");
        repository.findById(confirmEvent.getCartId()).filter(f -> f.isOpen()).ifPresent(cart -> {
            cart.checkout();
            repository.save(cart);
        });
    }

}