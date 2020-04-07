package com.rchiarinelli.eventsource.test.util;

import java.util.UUID;

import com.google.gson.JsonObject;

public class StubUtils {

    private StubUtils(){}

    public static JsonObject createOwnerDataStub() {
        JsonObject ownerData = new JsonObject();
        ownerData.addProperty("id", UUID.randomUUID().toString());
        ownerData.addProperty("name", "John Doe");
        ownerData.addProperty("documentType", "GovernmentIdentifier");
        ownerData.addProperty("address1", "Howth Road");
        ownerData.addProperty("address2", "Clontaf");
        ownerData.addProperty("address3", "D03");
        ownerData.addProperty("city", "Clontark");
        ownerData.addProperty("state", "Dublin");
        ownerData.addProperty("country", "Ireland");
        ownerData.addProperty("zipCode", "D03VY11");
        return ownerData;
    }

    public static JsonObject createGarageDataStub() {
        JsonObject garageData = new JsonObject();
        garageData.addProperty("id", UUID.randomUUID().toString());
        garageData.addProperty("latitude", 3.3D);
        garageData.addProperty("longitude", 3.1D);
        garageData.addProperty("ownerId", UUID.randomUUID().toString());
        garageData.addProperty("address1", "Howth Road");
        garageData.addProperty("address2", "Clontaf");
        garageData.addProperty("address3", "D03");
        garageData.addProperty("city", "Clontark");
        garageData.addProperty("state", "Dublin");
        garageData.addProperty("country", "Ireland");
        garageData.addProperty("zipCode", "D03VY11");
        return garageData;
    }

    public static JsonObject createGarageSlotStub() {
        JsonObject slotData = new JsonObject();
        slotData.addProperty("garageUUID", UUID.randomUUID().toString());
        slotData.addProperty("ownerUUID", UUID.randomUUID().toString());
        slotData.addProperty("periodType", "hour");
        slotData.addProperty("minimunTime", 1D);
        slotData.addProperty("maximunTime", 8D);
        slotData.addProperty("billingPeriodType", "hour");
        slotData.addProperty("valuePerPeriod", 30D);
        slotData.addProperty("available", true);
        slotData.addProperty("active", true);
        return slotData;
    }

}