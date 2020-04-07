package com.rchiarinelli.eventsource.query;

import java.util.Collections;

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

        if (event.getGarageSlotData().get("garageUUID") != null) {
            view.setGarageUUID(event.getGarageSlotData().get("garageUUID").getAsString());
        }

        if (event.getGarageSlotData().get("ownerUUID") != null) {
            view.setOwnerUUID(event.getGarageSlotData().get("ownerUUID").getAsString());
        }

        if (event.getGarageSlotData().get("periodType") != null) {
            view.setPeriodType(event.getGarageSlotData().get("periodType").getAsString());
        }

        if (event.getGarageSlotData().get("minimunTime") != null) {
            view.setMinimunTime(event.getGarageSlotData().get("minimunTime").getAsDouble());
        }

        if (event.getGarageSlotData().get("maximunTime") != null) {
            view.setMaximunTime(event.getGarageSlotData().get("maximunTime").getAsDouble());
        }

        if (event.getGarageSlotData().get("billingPeriodType") != null) {
            view.setBillingPeriodType(event.getGarageSlotData().get("billingPeriodType").getAsString());
        }

        if (event.getGarageSlotData().get("valuePerPeriod") != null) {
            view.setValuePerPeriod(event.getGarageSlotData().get("valuePerPeriod").getAsDouble());
        }

        if (event.getGarageSlotData().get("available") != null) {
            view.setAvailable(event.getGarageSlotData().get("available").getAsBoolean());
        }

        if (event.getGarageSlotData().get("active") != null) {
            view.setActive(event.getGarageSlotData().get("active").getAsBoolean());
        }

        if (event.getGarageSlotData().get("address1") != null) {
            view.setAddress1(event.getGarageSlotData().get("address1").getAsString());
        }

        if (event.getGarageSlotData().get("address2") != null) {
            view.setAddress2(event.getGarageSlotData().get("address2").getAsString());
        }

        if (event.getGarageSlotData().get("address3") != null) {
            view.setAddress3(event.getGarageSlotData().get("address3").getAsString());
        }

        if (event.getGarageSlotData().get("city") != null) {
            view.setCity(event.getGarageSlotData().get("city").getAsString());
        }

        if (event.getGarageSlotData().get("state") != null) {
            view.setState(event.getGarageSlotData().get("state").getAsString());
        }

        if (event.getGarageSlotData().get("country") != null) {
            view.setCountry(event.getGarageSlotData().get("country").getAsString());
        }

        if (event.getGarageSlotData().get("zipCode") != null) {
            view.setZipCode(event.getGarageSlotData().get("zipCode").getAsString());
        }

        if (event.getGarageSlotData().get("latitude") != null) {
            view.setLatitude(event.getGarageSlotData().get("latitude").getAsDouble());
        }

        if (event.getGarageSlotData().get("longitude") != null) {
            view.setLongitude(event.getGarageSlotData().get("longitude").getAsDouble());
        }

        if (event.getGarageSlotData().get("ownerName") != null) {
            view.setOwnerName(event.getGarageSlotData().get("ownerName").getAsString());
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