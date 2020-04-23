package com.rchiarinelli.eventsource.restresource;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.gson.JsonObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rchiarinelli.eventsource.coreapi.queries.GarageReservationSessionQuery;
import com.rchiarinelli.eventsource.k8s.ClientConfig;
import com.rchiarinelli.eventsource.query.GarageAggregateView;
import com.rchiarinelli.eventsource.restresource.input.GarageSlotInput;
import com.rchiarinelli.eventsource.service.GarageSlotService;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Value("${gig.core.url}")
    private String endpointUrl;

    @Autowired
    private GarageSlotService garageSlotService;

    public GarageAggregateResource(final CommandGateway commandGateway, final QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping(path = { "", "/" }, consumes = "application/json", produces =  "application/json")
    public ResponseEntity<?> createGarageSlot(@RequestBody final GarageSlotInput input) throws InterruptedException, ExecutionException {

        log.debug("Creating garage slot. Input Data: " + input);

        return this.garageSlotService.createGarageSlot(input);
    }

    @GetMapping(path = { "/owner", "/owner/" }, produces = "application/json")
    @HystrixCommand(fallbackMethod = "getFallbackName", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000") })
    public ResponseEntity<String> getOwners() {
        log.debug("Retrieving owners");

        final String url = endpointUrl + "/owner";

        final ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
        log.debug("Owners ", url, response);

        return response;
    }

    private ResponseEntity<String> getFallbackName() {
        final JsonObject json = new JsonObject();
        json.addProperty("Response", "Fallback");
        return ResponseEntity.ok(json.toString());
    }

    @GetMapping
    public String load() {
        log.debug("Loading configurations");
        String serviceList = "";
        if (discoveryClient != null) {
            final List<String> services = this.discoveryClient.getServices();
            log.debug("Services " + services);
            for (final String service : services) {

                final List<ServiceInstance> instances = this.discoveryClient.getInstances(service);
                log.debug("Instances " + instances);
                serviceList += ("[" + service + " : " + ((!CollectionUtils.isEmpty(instances)) ? instances.size() : 0) + " instances ]");
            }
        }

        return config.getMessage() + "  " + serviceList;
    }

     // TODO GET method to retrieve aggregate

     @GetMapping("/{garageAggregateId}")
     public CompletableFuture<GarageAggregateView> findFoodCart(
             @PathVariable("garageAggregateId") final String garageAggregateId) {
         return queryGateway.query(new GarageReservationSessionQuery(UUID.fromString(garageAggregateId)),
                 ResponseTypes.instanceOf(GarageAggregateView.class));
     }

}