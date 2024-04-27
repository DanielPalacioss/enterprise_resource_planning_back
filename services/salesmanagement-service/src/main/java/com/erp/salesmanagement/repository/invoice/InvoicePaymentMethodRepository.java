package com.erp.salesmanagement.repository.invoice;

import com.erp.salesmanagement.model.invoice.InvoicePaymentMethodModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoicePaymentMethodRepository extends JpaRepository<InvoicePaymentMethodModel, Long> {
    Optional<InvoicePaymentMethodModel> findByPaymentMethod(String paymentMethod);
    Optional<List<InvoicePaymentMethodModel>> findAllByStatus_Id(Boolean status);
}
