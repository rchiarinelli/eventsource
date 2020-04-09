package com.rchiarinelli.eventsource.restresource;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class GarageAggregateResourceTest {


    @Mock
    private RestTemplate mockedRestTemplate;

    @Mock
    private CommandGateway mockedCommandGateway;
    
    @Mock
    private QueryGateway mockedQueryGateway;

    @InjectMocks
    private GarageAggregateResource helloService = new GarageAggregateResource(mockedCommandGateway, mockedQueryGateway);

    @Test
    public void test_createGarageSlot() {

    }


}