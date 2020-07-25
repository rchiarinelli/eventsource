package com.rchiarinelli.eventsource.restresource;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import com.rchiarinelli.eventsource.restresource.input.CartInput;
import com.rchiarinelli.eventsource.service.core.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/cart")
@Log4j2
public class CartResource {

    @Autowired
    private CartService cartService;

    //Open cart
    @PostMapping(path = { "", "/" }, consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCart(@Valid @RequestBody final CartInput input) {
        log.debug("Opening cart");
        final Optional<UUID> openCartResponse = cartService.openCart(input.getCustomerId());
        
        ResponseEntity<String> response = null;

        if (openCartResponse.isPresent()) {
            response = ResponseEntity.ok(openCartResponse.get().toString());
        } else {
            response = ResponseEntity.badRequest().build();
        }

        return response;
    }

    @PutMapping(path = { "", "/" }, consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addItem(@RequestBody final CartInput input) {
        log.debug("Adding item to cart");
        final var addItemToCardResponse = cartService.addItemToCart(UUID.fromString(input.getCartId()),input.getCartId(), input.getServiceId(), input.getQuantity());
        
        ResponseEntity<String> response = null;

        if (addItemToCardResponse) {
            response = ResponseEntity.ok().build();
        } else {
            response = ResponseEntity.badRequest().build();
        }

        return response;
    }

    //Add item to cart

    //Update item

    //remove item 

    //checkout cart

    //cancel cart


}