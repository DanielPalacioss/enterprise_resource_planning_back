package com.salesmanagementplatform.invoices.service;

import java.time.LocalDate;

public record FilterFields(Long customer, LocalDate startDate, LocalDate finalDate) {
}
