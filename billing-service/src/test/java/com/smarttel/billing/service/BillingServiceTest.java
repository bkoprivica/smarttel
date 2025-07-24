package com.smarttel.billing.service;

import com.smarttel.billing.model.Billing;
import com.smarttel.billing.repository.BillingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillingServiceTest {

    @Mock
    private BillingRepository repository;

    @InjectMocks
    private BillingService billingService;

    @Test
    public void createBilling_savesBillingWithExpectedFields() {
        Long customerId = 42L;
        Billing saved = new Billing(customerId, BigDecimal.valueOf(99.99));
        when(repository.save(any(Billing.class))).thenReturn(saved);

        Billing result = billingService.createBilling(customerId);

        ArgumentCaptor<Billing> captor = ArgumentCaptor.forClass(Billing.class);
        verify(repository).save(captor.capture());
        Billing captured = captor.getValue();

        assertEquals(customerId, captured.getCustomerId());
        assertEquals(new BigDecimal("99.99"), captured.getAmount());
        assertSame(saved, result);
    }
}
