package com.erp.salesmanagement.repository.invoice;

import com.erp.salesmanagement.model.invoice.InvoiceStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatusModel, Long> {

    Optional<InvoiceStatusModel> findByStatus(String status);

}
