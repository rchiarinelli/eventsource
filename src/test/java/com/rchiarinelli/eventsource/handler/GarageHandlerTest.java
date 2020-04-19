package com.rchiarinelli.eventsource.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rchiarinelli.eventsource.coreapi.commands.CreateGarageSlotCommand;
import com.rchiarinelli.eventsource.test.util.StubUtils;

import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.ApplyMore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AggregateLifecycle.class)
public class GarageHandlerTest {

    @Mock
    private CreateGarageSlotCommand mockedCommand;

    @Mock
    private ApplyMore mockedApplyMore;

    @Test
    public void test_instantiateWithCommand() throws NoSuchFieldException, SecurityException {

        PowerMockito.mockStatic(AggregateLifecycle.class);
        
        when(
            AggregateLifecycle.apply(any()))
            .thenReturn(mockedApplyMore);


        when(mockedCommand.getOwnerData()).thenReturn(StubUtils.createOwnerDataStub().toString());
        when(mockedCommand.getGarageData()).thenReturn(StubUtils.createGarageDataStub().toString());
        when(mockedCommand.getSlotData()).thenReturn(StubUtils.createGarageSlotStub().toString());
        

        new GarageHandler(mockedCommand);
    }


}