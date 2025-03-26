package com.smarttel.notification.dto;

public class CustomerDTO {
    private Long id;
    // Optionally, add more fields if needed (e.g., name)

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
