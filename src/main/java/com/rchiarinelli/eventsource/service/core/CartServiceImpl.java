package com.rchiarinelli.eventsource.service.core;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.rchiarinelli.eventsource.coreapi.commands.cart.OpenCartCommand;
import com.rchiarinelli.eventsource.coreapi.commands.cart.UpdateCartItemCommand;
import com.rchiarinelli.eventsource.domain.aggregate.Cart;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CartServiceImpl implements CartService {

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    @Value("${gig.core.url}")
    private String endpointUrl;

    public CartServiceImpl(final CommandGateway commandGateway, final QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @Override
    public Optional<UUID> openCart(final String customerId) {
        log.debug("Sending command OpenCartCommand");
        final CompletableFuture<UUID> resp = commandGateway
                .send(OpenCartCommand.builder().cartId(UUID.randomUUID()).customerId(customerId).build());
        try {
            return Optional.ofNullable(resp.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error(e);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public boolean addItemToCart(final UUID cartId, final String providerId, final String serviceId,
            final Integer quantity) {
        log.debug("Sending command ");

        final var resp = commandGateway.send(UpdateCartItemCommand.builder().cartId(cartId)
                .providerId(providerId).serviceId(serviceId).quantity(quantity).build());
        try {
            log.debug(resp);
            final var r = resp.get();
            log.debug(r);
            return Boolean.TRUE;
        } catch (InterruptedException | ExecutionException e) {
           log.error(e);
        }

        return Boolean.FALSE;
    }

    @Override
    public boolean updateCartItem(final UUID cartId, final String providerId, final String serviceId,
            final Integer quantity) {
        log.debug("Sending command ");
        return Boolean.FALSE;
    }

    @Override
    public boolean removeItemFromCart(final UUID cartId, final String providerId, final String serviceId,
            final Integer quantity) {
        log.debug("Sending command ");
        return Boolean.FALSE;
    }

    @Override
    public boolean checkoutCart(final UUID cartId) {
        log.debug("Sending command ");
        return Boolean.FALSE;
    }

    @Override
    public boolean cancelCart(final UUID cartId) {
        log.debug("Sending command ");
        return Boolean.FALSE;
    }

    @Override
    public List<Cart> getCarts(String customerId) {

        return null;
    }

    @Override
    public List<Cart> getCart(UUID cartId, String customerId) {
        // TODO Auto-generated method stub
        return null;
    }

}