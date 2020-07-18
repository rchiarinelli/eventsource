package com.rchiarinelli.eventsource.domain.repositories;

import java.util.UUID;

import com.rchiarinelli.eventsource.domain.aggregate.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}