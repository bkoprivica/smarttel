package com.smarttel.billing.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Billing implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    private Long customerId;
    private BigDecimal amount;

    public Billing() {
    }

    public Billing(Long customerId, BigDecimal amount) {
        this.customerId = customerId;
        this.amount = amount;
    }

    public Long getId() {
      return id;
    }
    
    public void setId(Long id) {
      this.id = id;
    }
    
    public Long getCustomerId() {
      return customerId;
    }
    
    public void setCustomerId(Long customerId) {
      this.customerId = customerId;
    }
    
    public BigDecimal getAmount() {
      return amount;
    }
    
    public void setAmount(BigDecimal amount) {
      this.amount = amount;
    }
}
