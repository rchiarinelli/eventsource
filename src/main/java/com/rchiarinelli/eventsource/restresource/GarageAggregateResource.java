package com.rchiarinelli.eventsource.restresource;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rchiarinelli.eventsource.coreapi.commands.CreateGarageSlotCommand;
import com.rchiarinelli.eventsource.k8s.ClientConfig;
import com.rchiarinelli.eventsource.restresource.input.GarageSlotInput;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/garageaggregate")
@Log4j2
public class GarageAggregateResource {

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ClientConfig config;

    @Autowired
    private RestTemplate restTemplate;

    public GarageAggregateResource(final CommandGateway commandGateway, final QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping(path = { "", "/" }, consumes = "application/json", produces =  "application/json")
    public ResponseEntity<?> createGarageSlot(final GarageSlotInput input) throws InterruptedException, ExecutionException {

        log.debug("Creating garage slot.");

        //Retrive garage info (address, geo location) and owner name
        ResponseEntity<String> ownerResponse = this.restTemplate.getForEntity("http://ambassador/backend/garage/owner/"+input.getOwnerUUID(), String.class);

        if (ownerResponse.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<String>(ownerResponse.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity<String> garageResponse =  this.restTemplate.getForEntity("http://ambassador/backend/garage/garage/"+input.getGarageUUID(), String.class);

        if (garageResponse.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<String>(garageResponse.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Call to create a new garage slot into garage-core
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://ambassador/backend/garage/owner", input, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<String>(response.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        JsonObject garageData = (JsonObject) JsonParser.parseString(garageResponse.getBody());
        JsonObject ownerData = (JsonObject) JsonParser.parseString(ownerResponse.getBody());

        Gson gson = new Gson();
        ;

        final CompletableFuture<UUID> resp = commandGateway.send(
                            new CreateGarageSlotCommand(
                                UUID.randomUUID()
                                ,garageData
                                ,ownerData
                                ,gson.toJsonTree(input).getAsJsonObject()));

        return ResponseEntity.ok(resp.get());
    }

    @GetMapping(path = {"/owner","/owner/"},produces="application/json")
    @HystrixCommand(fallbackMethod = "getFallbackName", commandProperties = {
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
    public ResponseEntity<String> getOwners() {
        log.debug("Retrieving owners");


        String url = "http://ambassador/backend/garage/owner";

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
        log.debug("Owners ",url,response);

        return response;
    }

    private ResponseEntity<String> getFallbackName() {
        JsonObject json = new JsonObject();
        json.addProperty("Response", "Fallback");
        return ResponseEntity.ok(json.toString());
    }


    @GetMapping
    public String load() {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://ambassador/backend/garage/owner";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        String serviceList = "";
        if (discoveryClient != null) {
            List<String> services = this.discoveryClient.getServices();

            for (String service : services) {

                List<ServiceInstance> instances = this.discoveryClient.getInstances(service);

                serviceList += ("[" + service + " : " + ((!CollectionUtils.isEmpty(instances)) ? instances.size() : 0) + " instances ]");
            }
        }

        return String.format(config.getMessage(), response.getBody(), serviceList);
    }

}