package com.rchiarinelli.eventsource.query;

import java.util.Map;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.springframework.data.jpa.repository.JpaRepository;

@Entity
public class GarageSlotView {

    @Id
    private UUID garageSlotId;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<UUID, Integer> slots;

    public GarageSlotView() {}


    public GarageSlotView(UUID garageSlotId, Map<UUID, Integer> slots) {
        this.garageSlotId = garageSlotId;
        this.slots = slots;
    }

    public UUID getGarageSlotId() {
        return garageSlotId;
    }

    public void setGarageSlotId(UUID garageSlotId) {
        this.garageSlotId = garageSlotId;
    }

    public Map<UUID, Integer> getSlots() {
        return slots;
    }

    public void setSlots(Map<UUID, Integer> slots) {
        this.slots = slots;
    }



}

interface GarageSlotViewRepository extends JpaRepository<GarageSlotView, UUID>{}