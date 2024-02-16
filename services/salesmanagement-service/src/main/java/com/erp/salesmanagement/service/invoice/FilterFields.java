package com.erp.salesmanagement.service.invoice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record FilterFields(@Min(value = 1, message = "the client id must be greater than or equal to 1")
                           Long customer,
                           @Past(message = "The date must be in the past.") LocalDate startDate,
                           LocalDate finalDate) {
}
