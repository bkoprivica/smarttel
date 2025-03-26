package com.smarttel.billing.dto;

public class CustomerDTO {
    private Long id;

    public CustomerDTO() {}

    public CustomerDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
