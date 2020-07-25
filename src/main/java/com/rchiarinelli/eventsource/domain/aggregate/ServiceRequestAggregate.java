package com.rchiarinelli.eventsource.domain.aggregate;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class ServiceRequestAggregate {

    public static enum ServiceRequestStatus {
        ACCEPTED, PEDING, REJECTED;
    }

    public static enum ServiceRequestStatusReason {
        REJECTED_UNAVAILABLE, OUT_OF_SERVICE;
    }

    @Id
    private UUID serviceRequestAggregateId;

    @Column(nullable = true)
    private String serviceRequestId;

    @Column(nullable = false)
    private UUID cartId;

    @Column(nullable = false)
    @Builder.Default
    private ServiceRequestStatus status = ServiceRequestStatus.PEDING;

    @Column(nullable = true)
    private ServiceRequestStatusReason statusReason;


    public ServiceRequestAggregate() {}

    public ServiceRequestAggregate(UUID serviceRequestAggregateId, String serviceRequestId, UUID cartId,
            ServiceRequestStatus status, ServiceRequestStatusReason statusReason) {
        this.serviceRequestAggregateId = serviceRequestAggregateId;
        this.serviceRequestId = serviceRequestId;
        this.cartId = cartId;
        this.status = status;
        this.statusReason = statusReason;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cartId == null) ? 0 : cartId.hashCode());
        result = prime * result + ((serviceRequestAggregateId == null) ? 0 : serviceRequestAggregateId.hashCode());
        result = prime * result + ((serviceRequestId == null) ? 0 : serviceRequestId.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((statusReason == null) ? 0 : statusReason.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServiceRequestAggregate other = (ServiceRequestAggregate) obj;
        if (cartId == null) {
            if (other.cartId != null)
                return false;
        } else if (!cartId.equals(other.cartId))
            return false;
        if (serviceRequestAggregateId == null) {
            if (other.serviceRequestAggregateId != null)
                return false;
        } else if (!serviceRequestAggregateId.equals(other.serviceRequestAggregateId))
            return false;
        if (serviceRequestId == null) {
            if (other.serviceRequestId != null)
                return false;
        } else if (!serviceRequestId.equals(other.serviceRequestId))
            return false;
        if (status != other.status)
            return false;
        if (statusReason != other.statusReason)
            return false;
        return true;
    }

    public void accept() {
        setStatus(ServiceRequestStatus.ACCEPTED);
    }

    public void reject(final ServiceRequestStatusReason reason) {
        setStatus(ServiceRequestStatus.REJECTED);
        setStatusReason(reason);
    }

    public void associateCoreServiceRequest(final String serviceRequestId) {
        setServiceRequestId(serviceRequestId);
        setStatus(ServiceRequestStatus.PEDING);
    }

    public boolean isAccepted() {
        return getStatus() == ServiceRequestStatus.ACCEPTED;
    }

    public boolean isPending() {
        return getStatus() == ServiceRequestStatus.PEDING;
    }

    public boolean isRejected() {
        return getStatus() == ServiceRequestStatus.REJECTED;
    }
}