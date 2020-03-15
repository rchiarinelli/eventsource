package com.rchiarinelli.eventsource.restresource;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.rchiarinelli.eventsource.coreapi.commands.CreateGarageSlotCommand;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/garage")
@Log4j2
public class GarageResource {

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    public GarageResource(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<?> createGarageSlot() throws InterruptedException, ExecutionException {

        log.debug("Creating garage slot.");

        CompletableFuture<UUID> resp = commandGateway.send(new CreateGarageSlotCommand(UUID.randomUUID()));
        
        return ResponseEntity.ok(resp.get());
    }


    
}