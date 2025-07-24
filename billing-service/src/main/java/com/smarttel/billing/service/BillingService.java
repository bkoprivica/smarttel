package com.smarttel.billing.service;

import com.smarttel.billing.model.Billing;
import com.smarttel.billing.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BillingService {

    @Autowired
    private BillingRepository repository;

    public Billing createBilling(Long customerId) {
        Billing bill = new Billing(customerId, new BigDecimal("99.99"));
        return repository.save(bill);
    }
}
