package com.rchiarinelli.eventsource.domain.repositories;

import java.util.UUID;

import com.rchiarinelli.eventsource.domain.aggregate.ServiceRequestAggregate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestAggregateRepository extends JpaRepository<ServiceRequestAggregate, UUID> {
    
}