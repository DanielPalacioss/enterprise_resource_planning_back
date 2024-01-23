package com.salesmanagementplatform.invoices.repository;

import com.salesmanagementplatform.invoices.model.InvoicePaymentMethodModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoicePaymentMethodRepository extends JpaRepository<InvoicePaymentMethodModel, Long> {
    InvoicePaymentMethodModel findByPaymentMethod(String paymentMethod);

    List<InvoicePaymentMethodModel> findAllByStatus_Id(Boolean status);
}
