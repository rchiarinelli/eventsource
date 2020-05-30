package com.rchiarinelli.eventsource.restresource.input;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

public class ServiceProviderInput {

    @Getter
    @Setter
    public static class ServiceDetailsInput {

        private String id;

        private String serviceName;

        private String serviceImageName;
    }

    @Getter
    @Setter
    public static class RecommendationDetailsInput {

        private String id;

        private String level;

        private String recommendedBy;
    }

    @Getter
    @Setter
    private String providerId;

    @Getter
    @Setter
    private String providerName;

    @Getter
    @Setter
    private String providerImg;

    @Getter
    @Setter
    private double reviewRate;

    @Setter
    private ServiceDetailsInput details;

    @Setter
    private RecommendationDetailsInput recommendationDetails;

    public Optional<ServiceDetailsInput> getDetails() {
        return Optional.of(details);
    }

    public Optional<RecommendationDetailsInput> getRecommendationDetails() {
        return Optional.of(recommendationDetails);
    }

    
}