package com.erp.salesmanagement.repository.invoice;

import com.erp.salesmanagement.model.invoice.InvoiceStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatusModel, Long> {

    InvoiceStatusModel findByStatus(String status);

}
