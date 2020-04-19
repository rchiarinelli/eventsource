package com.rchiarinelli.eventsource.query;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import com.google.gson.JsonObject;
import com.rchiarinelli.eventsource.coreapi.events.GarageSlotCreatedEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class GarageReservationProjectorTest {

    @Mock
    private GarageSlotViewRepository mockedGarageSlotViewRepository;

    @Mock
    private GarageSlotCreatedEvent mockedEvent;
    
    @Test
    public void test_OnCreatedEvent() {

        JsonObject garageSlotData = new JsonObject();
        
        garageSlotData.addProperty("garageUUID", UUID.randomUUID().toString());

        garageSlotData.addProperty("ownerUUID", UUID.randomUUID().toString());
        garageSlotData.addProperty("ownerName", "John Doe");

        garageSlotData.addProperty("periodType", "hour");
        garageSlotData.addProperty("minimunTime", 1);
        garageSlotData.addProperty("maximunTime", 8);
        garageSlotData.addProperty("billingPeriodType", "hour");
        garageSlotData.addProperty("valuePerPeriod", 2.5);
        garageSlotData.addProperty("available", true);
        garageSlotData.addProperty("active", true);

        garageSlotData.addProperty("address1", "Grafton Street");
        garageSlotData.addProperty("address2", "");
        garageSlotData.addProperty("address3", "");
        garageSlotData.addProperty("city", "Dublin");
        garageSlotData.addProperty("state", "Dublin");
        garageSlotData.addProperty("country", "Ireland");
        garageSlotData.addProperty("zipCode", "D03VY11");
        garageSlotData.addProperty("latitude", 33.2);
        garageSlotData.addProperty("longitude", 33.2);


        when(this.mockedEvent.getGarageSlotId()).thenReturn(UUID.randomUUID());
        when(this.mockedEvent.getGarageSlotData()).thenReturn(garageSlotData.toString());
        

        GarageReservationProjector projector = new GarageReservationProjector(this.mockedGarageSlotViewRepository);
        projector.on(this.mockedEvent);

    }

    

}