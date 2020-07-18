package com.rchiarinelli.eventsource.domain.aggregate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
public class Cart {

    public static enum CartStatus {
        OPEN,
        CANCELLED,
        CHECKOUT;
    }
    
    @Id
    private UUID id;

    private String customerId;

    @ElementCollection
    private Map<String,Map<String,Integer>> selectedServices;

    @Column (nullable = false)
    @Builder.Default
    private CartStatus status = CartStatus.OPEN;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((selectedServices == null) ? 0 : selectedServices.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
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
        Cart other = (Cart) obj;
        if (customerId == null) {
            if (other.customerId != null)
                return false;
        } else if (!customerId.equals(other.customerId))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (selectedServices == null) {
            if (other.selectedServices != null)
                return false;
        } else if (!selectedServices.equals(other.selectedServices))
            return false;
        if (status != other.status)
            return false;
        return true;
    }

    /**
     * Add a selected service from a provider to to the cart using the service's identifier and provider's identifier.
     *
     * @param providerId the provider identifier 
     * @param serviceId the service identifier
     */
    public void addService(String providerId,String serviceId, Integer quantity) {
        if (getSelectedServices() == null) {
    		selectedServices = new HashMap<>();
    	}
        getSelectedServices().computeIfAbsent(providerId, m -> createItemDetails(serviceId, quantity));
    }


    /**
     * 
     * 
     * @param serviceId
     * @param quantity
     * @return
     */
    Map<String,Integer> createItemDetails(String serviceId, Integer quantity) {
        Map<String,Integer> item = new HashMap<>();
        item.put(serviceId, quantity);
        return item;
    }

    /**
     * Remove the service from the cart using the provider id and service identfier.
     * 
     * @param providerId the provideId
     * @param serviceId the service indenfier
     */
    public void removeServiceItem(String providerId, String serviceId) {
        getSelectedServices().computeIfPresent(providerId, (serviceIdKey,m) ->{ m.remove(serviceIdKey); return m; });
    }

    /**
     * Updates the provider's service item with a new quantity.
     * 
     * @param providerId
     * @param serviceId
     * @param quantity
     */
    public void updateServiceItem(String providerId, String serviceId,Integer quantity) {
        if (getSelectedServices() == null) {
    		selectedServices = new HashMap<>();
    	}
        getSelectedServices().computeIfPresent(providerId, (serviceIdKey,m) ->{ m.putIfAbsent(serviceIdKey, quantity); return m; });
    }

    public void cancel() {
        setStatus(CartStatus.CANCELLED);
    }

    public void checkout() {
        setStatus(CartStatus.CHECKOUT);
    }

    public boolean isOpen() {
        return getStatus() == CartStatus.OPEN;
    }

    public boolean isCancelled() {
        return getStatus() == CartStatus.CANCELLED;
    }

    public boolean isConfirmed() {
        return getStatus() == CartStatus.CHECKOUT;
    }
}
