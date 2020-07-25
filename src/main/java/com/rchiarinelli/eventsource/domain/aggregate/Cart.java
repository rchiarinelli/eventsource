package com.rchiarinelli.eventsource.domain.aggregate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Cart {

    public static enum CartStatus {
        OPEN, CANCELLED, CHECKOUT;
    }

    @Id
    private UUID id;

    @Column(nullable = false)
    private String customerId;

    @OneToMany(mappedBy="cart",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<CartItem> selectedServices = new HashSet<>();

    @Column(nullable = false)
    @Builder.Default
    private CartStatus status = CartStatus.OPEN;


    public Cart() {
    }

    public Cart(UUID id, String customerId, Set<CartItem> selectedServices, CartStatus status) {
        this.id = id;
        this.customerId = customerId;
        this.selectedServices = selectedServices;
        this.status = status;
    }


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
     * Add a selected service from a provider to to the cart using the service's
     * identifier and provider's identifier.
     *
     * @param providerId the provider identifier
     * @param serviceId  the service identifier
     */
    public void addService(String providerId, String serviceId, Integer quantity) {
        getSelectedServices().add(CartItem.builder().id(UUID.randomUUID()).providerId(providerId).serviceId(serviceId).quantity(quantity).cart(this).build());
    }

    /**
     * Remove the service from the cart using the provider id and service identfier.
     * 
     * @param providerId the provideId
     * @param serviceId  the service indenfier
     */
    public void removeServiceItem(String providerId, String serviceId) {
        getSelectedServices().removeIf(f -> f.getProviderId().equals(providerId) && f.getServiceId().equals(serviceId));
    }

    /**
     * Updates the provider's service item with a new quantity.
     * 
     * @param providerId
     * @param serviceId
     * @param quantity
     */
    public void updateServiceItem(String providerId, String serviceId, Integer quantity) {
        getSelectedServices().stream().filter(f -> f.getProviderId().equals(providerId) && f.getServiceId().equals(serviceId)).findFirst().ifPresentOrElse(item->item.setQuantity(quantity),
        new Runnable(){
            public void run() {
                addService(providerId, serviceId, quantity);
            }
        });
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
