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
        // FoodCartView foodCartView = new FoodCartView(event.getFoodCartId(),
        // Collections.emptyMap());
        // foodCartViewRepository.save(foodCartView);

        log.debug("Persisting new garage slot view aggregate.");

        GarageSlotView view = new GarageSlotView(event.getGarageSlotId(), Collections.EMPTY_MAP);
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