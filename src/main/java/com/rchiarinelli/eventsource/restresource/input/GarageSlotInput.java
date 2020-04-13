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

    private String garageUUID;

    private String ownerUUID;

    @NotNull
    @NotEmpty
    private String periodType;

    @NotNull
    @NotEmpty
    private double minimunTime;

    @NotNull
    @NotEmpty
    private double maximunTime;

    @NotNull
    @NotEmpty
    private String billingPeriodType;

    @NotNull
    @NotEmpty
    private double valuePerPeriod;

    @NotNull
    @NotEmpty
    private boolean available;

    @NotNull
    @NotEmpty
    private boolean active;

}