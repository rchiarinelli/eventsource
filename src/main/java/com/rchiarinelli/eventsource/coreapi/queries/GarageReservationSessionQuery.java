package com.rchiarinelli.eventsource.coreapi.queries;

import java.util.UUID;

import lombok.Getter;

@Getter
public class GarageReservationSessionQuery {


    private UUID garageSlodId;

    public GarageReservationSessionQuery(UUID garageSlodId) {
        this.garageSlodId = garageSlodId;
    }

}