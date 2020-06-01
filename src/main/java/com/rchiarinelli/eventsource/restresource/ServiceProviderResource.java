package com.rchiarinelli.eventsource.restresource;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.RecommendationDetailsInput;
import com.rchiarinelli.eventsource.restresource.input.ServiceProviderInput.ServiceDetailsInput;
import com.rchiarinelli.eventsource.service.ServiceProviderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/serviceprovideraggregate")
@Log4j2
public class ServiceProviderResource {

    private static final String SERVICE_PATH = "/services";

    private static final String RECOMMENDATION_PATH = "/recommendations";


    @Autowired
    private ServiceProviderService service;

    @PostMapping(path = { "", "/" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(@Valid @RequestBody final ServiceProviderInput reqData) {
        final var resp = service.createServiceProviderAggregate(reqData);
        log.debug("Building response");
        return ResponseEntity.ok(resp.toString());
    }

    @PostMapping(path = { "/{uuid}/" + SERVICE_PATH  }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addService(@PathVariable("uuid") @NotNull final String uuid, @Valid @RequestBody  final ServiceDetailsInput serviceDetails) {
        return ResponseEntity.ok(service.addServiceToProvider(UUID.fromString(uuid),serviceDetails));
    }

    @PostMapping(path = { "/{uuid}/" + RECOMMENDATION_PATH }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRecommendation(@PathVariable("uuid") @NotNull final String uuid, @Valid @RequestBody  final RecommendationDetailsInput  recommendationData) {
        return ResponseEntity.ok(service.addRecommendationToProvider(UUID.fromString(uuid), recommendationData));
    }
}