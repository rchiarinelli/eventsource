package com.rchiarinelli.eventsource.query;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class GarageAggregateView {

    @Id
    private UUID garageSlotId;

    @Column
    private String garageUUID;

    @Column
    private String ownerUUID;

    @Column
    private String periodType;

    @Column
    private double minimunTime;

    @Column
    private double maximunTime;

    @Column
    private String billingPeriodType;

    @Column
    private double valuePerPeriod;

    @Column
    private boolean available;

    @Column
    private boolean active;

    @Column
    private String address1;

    @Column
    private String address2;

    @Column
    private String address3;

    @Column
    private String city;

    @Column
    private String state;
    
    @Column
    private String country;

    @Column
    private String zipCode;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private String ownerName;

}

interface GarageSlotViewRepository extends JpaRepository<GarageAggregateView, UUID>{}