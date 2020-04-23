package com.rchiarinelli.eventsource.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rchiarinelli.eventsource.coreapi.commands.CreateGarageSlotCommand;
import com.rchiarinelli.eventsource.restresource.input.GarageSlotInput;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GarageSlotService {

    private CommandGateway commandGateway;
    
    private QueryGateway queryGateway;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gig.core.url}")
    private String endpointUrl;

	public GarageSlotService(final CommandGateway commandGateway, final QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
	}

  	private ResponseEntity<?> createGarageSlotFallback(final GarageSlotInput input) {
        final JsonObject json = new JsonObject();
        json.addProperty("Response", "Fallback");
        return ResponseEntity.ok(json.toString());
    }
    
    @HystrixCommand(fallbackMethod = "createGarageSlotFallback", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000") })
    public ResponseEntity<?> createGarageSlot(final GarageSlotInput input) throws InterruptedException, ExecutionException {
        //Retrive garage info (address, geo location) and owner name

        final ResponseEntity<String> ownerResponse = this.restTemplate
                .getForEntity(endpointUrl+"/owner/" + input.getOwnerUUID(), String.class);

        if (ownerResponse.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<String>(ownerResponse.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final ResponseEntity<String> garageResponse = this.restTemplate
                .getForEntity(endpointUrl+"/garage/" + input.getGarageUUID(), String.class);

        if (garageResponse.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<String>(garageResponse.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        log.debug("OwnerResponse " + ownerResponse.getBody());

        log.debug("GarageResponse " + garageResponse.getBody());

        final Gson gson = new Gson();

        //Prepare call
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String reqJsonContent = gson.toJson(input);
        
        final ResponseEntity<String> response = this.restTemplate.postForEntity(endpointUrl+"/garageslot/", new HttpEntity<String>(reqJsonContent, headers), String.class);

        log.debug("New Slot Response " + response.getBody());

        if (response.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<String>(response.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final CompletableFuture<UUID> resp = commandGateway.send(new CreateGarageSlotCommand(UUID.randomUUID(),
        garageResponse.getBody(), ownerResponse.getBody(), reqJsonContent));

        return ResponseEntity.ok(resp.get());

    }






}