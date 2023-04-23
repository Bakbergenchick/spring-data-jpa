package com.spring.sdjpa.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity{
    private String customerName;
    @Embedded
    private Address address;
    private String phone;
    private String email;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private Set<OrderHeader> orderHeaderSet;

    public void addOrderHeaderToCustomer(OrderHeader orderHeader){
        if (orderHeaderSet == null){
            orderHeaderSet = new HashSet<>();
        }

        orderHeaderSet.add(orderHeader);
        orderHeader.setCustomer(this);
    }


}
