package com.rchiarinelli.eventsource.query;

import java.util.UUID;

import com.rchiarinelli.eventsource.domain.aggregate.serviceprovider.ServiceProviderAggregate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderAggregateRepository extends JpaRepository<ServiceProviderAggregate, UUID> {
}
