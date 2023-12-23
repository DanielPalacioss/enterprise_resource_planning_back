package com.salesmanagementplatform.invoices.repository;

import com.salesmanagementplatform.invoices.model.InvoicePaymentMethodModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoicePaymentMethodRepository extends JpaRepository<InvoicePaymentMethodModel, Long> {

    InvoicePaymentMethodModel findByPaymentMethod(String paymentMethod);

}
