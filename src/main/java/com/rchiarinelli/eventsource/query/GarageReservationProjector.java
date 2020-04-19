package com.rchiarinelli.eventsource.query;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rchiarinelli.eventsource.coreapi.events.GarageSlotCreatedEvent;
import com.rchiarinelli.eventsource.coreapi.events.GarageSlotReservedEvent;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class GarageReservationProjector {

    private final GarageSlotViewRepository garageSlotViewRepository;

    public GarageReservationProjector(GarageSlotViewRepository foodCartViewRepository) {
        this.garageSlotViewRepository = foodCartViewRepository;
    }

    @EventHandler
    public void on(GarageSlotCreatedEvent event) {
 
        GarageAggregateView view = new GarageAggregateView();
        view.setGarageSlotId(event.getGarageSlotId());

        log.debug("Persisting new garage slot view aggregate.");

        final JsonObject slotData = (JsonObject)  JsonParser.parseString(event.getGarageSlotData());

        if (slotData.get("garageUUID") != null) {
            view.setGarageUUID(slotData.get("garageUUID").getAsString());
        }

        if (slotData.get("ownerUUID") != null) {
            view.setOwnerUUID(slotData.get("ownerUUID").getAsString());
        }

        if (slotData.get("periodType") != null) {
            view.setPeriodType(slotData.get("periodType").getAsString());
        }

        if (slotData.get("minimunTime") != null) {
            view.setMinimunTime(slotData.get("minimunTime").getAsDouble());
        }

        if (slotData.get("maximunTime") != null) {
            view.setMaximunTime(slotData.get("maximunTime").getAsDouble());
        }

        if (slotData.get("billingPeriodType") != null) {
            view.setBillingPeriodType(slotData.get("billingPeriodType").getAsString());
        }

        if (slotData.get("valuePerPeriod") != null) {
            view.setValuePerPeriod(slotData.get("valuePerPeriod").getAsDouble());
        }

        if (slotData.get("available") != null) {
            view.setAvailable(slotData.get("available").getAsBoolean());
        }

        if (slotData.get("active") != null) {
            view.setActive(slotData.get("active").getAsBoolean());
        }

        if (slotData.get("address1") != null) {
            view.setAddress1(slotData.get("address1").getAsString());
        }

        if (slotData.get("address2") != null) {
            view.setAddress2(slotData.get("address2").getAsString());
        }

        if (slotData.get("address3") != null) {
            view.setAddress3(slotData.get("address3").getAsString());
        }

        if (slotData.get("city") != null) {
            view.setCity(slotData.get("city").getAsString());
        }

        if (slotData.get("state") != null) {
            view.setState(slotData.get("state").getAsString());
        }

        if (slotData.get("country") != null) {
            view.setCountry(slotData.get("country").getAsString());
        }

        if (slotData.get("zipCode") != null) {
            view.setZipCode(slotData.get("zipCode").getAsString());
        }

        if (slotData.get("latitude") != null) {
            view.setLatitude(slotData.get("latitude").getAsDouble());
        }

        if (slotData.get("longitude") != null) {
            view.setLongitude(slotData.get("longitude").getAsDouble());
        }

        if (slotData.get("ownerName") != null) {
            view.setOwnerName(slotData.get("ownerName").getAsString());
        }

        this.garageSlotViewRepository.save(view);

    }

    @EventHandler
    public void on(GarageSlotReservedEvent event) {
        // foodCartViewRepository.findById(event.getFoodCartId()).ifPresent(
        // foodCartView -> foodCartView.addProducts(event.getProductId(),
        // event.getQuantity())
        // );

        log.debug("Reserving garage slot for provided user");

    }

/*     // The Event store `EmbeddedEventStore` delegates actual storage and retrieval
    // of events to an `EventStorageEngine`.
    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
        return EmbeddedEventStore.builder().storageEngine(storageEngine)
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore")).build();
    }

    // The `MongoEventStorageEngine` stores each event in a separate MongoDB
    // document
    @Bean
    public EventStorageEngine storageEngine(MongoClient client) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build()).build();
    }
 */
}