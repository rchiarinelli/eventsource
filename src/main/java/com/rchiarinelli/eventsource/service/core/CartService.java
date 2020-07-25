package com.rchiarinelli.eventsource.service.core;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.rchiarinelli.eventsource.domain.aggregate.Cart;
/**
 * 
 */
public interface CartService {
    /**
     * 
     * @param customer
     * @return
     */
    Optional<UUID> openCart(final String customer);
    
    boolean addItemToCart(final UUID cartId, final String providerId, final String serviceId, Integer quantity);

    boolean updateCartItem(final UUID cartId, final String providerId, final String serviceId, Integer quantity);

    boolean removeItemFromCart(final UUID cartId, final String providerId, final String serviceId, Integer quantity);

    boolean checkoutCart(final UUID cartId);

    boolean cancelCart(final UUID cartId);

    List<Cart> getCarts(final String customerId);

    List<Cart> getCart(final UUID cartId, final String customerId);

}