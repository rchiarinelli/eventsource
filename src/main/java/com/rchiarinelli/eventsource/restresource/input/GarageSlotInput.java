package com.rchiarinelli.eventsource.restresource.input;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class GarageSlotInput {

    @NotNull
    @NotEmpty
    private String garageUUID;

    @NotEmpty
    @NotNull
    private String ownerUUID;

    @NotNull
    @NotEmpty
    private String periodType;

    @NotNull
    private double minimunTime;

    @NotNull
    private double maximunTime;

    @NotNull
    @NotEmpty
    private String billingPeriodType;

    @NotNull
    private double valuePerPeriod;

    @NotNull
    private boolean available;

    @NotNull
    private boolean active;

}