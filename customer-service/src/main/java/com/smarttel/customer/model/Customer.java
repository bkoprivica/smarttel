package com.smarttel.customer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    private String email;

    public Customer() {
    }
    
    public Customer(String name, String email) {
      this.name = name;
      this.email = email;
    }
    
    public Long getId() {
      return id;
    }
    
    public void setId(Long id) {
      this.id = id;
    }
    
    public String getName() {
      return name;
    }
    
    public void setName(String name) {
      this.name = name;
    }
    
    public String getEmail() {
      return email;
    }
    
    public void setEmail(String email) {
      this.email = email;
    }
}
