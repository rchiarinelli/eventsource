package com.rchiarinelli.eventsource.restresource;

import java.util.concurrent.ExecutionException;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@SpringBootTest()
@Profile("test")
public class GarageAggregateResourceTest {

    @Mock
    private RestTemplate mockedRestTemplate;

    @Mock
    private CommandGateway mockedCommandGateway;

    @Mock
    private QueryGateway mockedQueryGateway;

    @InjectMocks
    private GarageAggregateResource garageResource = new GarageAggregateResource(mockedCommandGateway,
            mockedQueryGateway);

    @Value("${gig.core.url}")
    private String endpointUrl;

    @Test
    public void test_createGarageSlot() throws InterruptedException, ExecutionException {
        //final GarageSlotInput input = new GarageSlotInput();
        Whitebox.setInternalState(garageResource, "endpointUrl", this.endpointUrl);
        // this.garageResource.createGarageSlot(input);
    }
}