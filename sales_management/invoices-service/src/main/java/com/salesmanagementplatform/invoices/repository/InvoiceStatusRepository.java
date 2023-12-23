package com.salesmanagementplatform.invoices.repository;

import com.salesmanagementplatform.invoices.model.InvoiceStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatusModel, Long> {

    InvoiceStatusModel findByStatus(String status);

}
