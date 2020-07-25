package com.rchiarinelli.eventsource.service.core;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rchiarinelli.eventsource.domain.aggregate.Cart;
import com.rchiarinelli.eventsource.domain.aggregate.ServiceRequestAggregate.ServiceRequestStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ServiceRequestServiceImpl implements ServiceRequestService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gig.core.url}")
    private String endpointUrl;

    public Optional<String> addServiceRequestFallback(final Cart cart) {
        final JsonObject json = new JsonObject();
        json.addProperty("Response", "Fallback");
        return Optional.ofNullable(json.toString());
    }

    @HystrixCommand(fallbackMethod = "addServiceRequestFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000") })
    @Override
    public Optional<String> addServiceRequest(final Cart cart) {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final var json = new JsonObject();

        json.addProperty("customerId", cart.getCustomerId());
        cart.getSelectedServices().stream().findFirst()
                .ifPresent(cartItem -> json.addProperty("serviceProviderId", cartItem.getProviderId()));
        json.addProperty("creationDate", DateFormat.getInstance().format(new Date()));
        json.addProperty("statusDate", DateFormat.getInstance().format(new Date()));
        json.addProperty("status", ServiceRequestStatus.PEDING.toString());
        json.addProperty("scheduledData", LocalDate.now().toString());
        json.addProperty("scheduledTime", LocalTime.now().toString());

        final var reqJsonContent = json.toString();

        final var response = this.restTemplate.postForEntity(endpointUrl + "/servicerequest/",
                new HttpEntity<String>(reqJsonContent, headers), String.class);

        if (response.getStatusCode() != HttpStatus.OK) {

        }

        log.debug("New Slot Response " + response.getBody());

        Gson gson = new Gson();

        final var responseJson = gson.toJsonTree(response.getBody());

        return Optional.of(responseJson.getAsJsonObject().get("id").getAsString());
    }

}