package com.salesmanagementplatform.invoices.repository;

import com.salesmanagementplatform.invoices.model.InvoiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceModel, Long> {

    @Query(value = "SELECT i.* FROM sc_business_management_platform.orders AS o, sc_business_management_platform.invoice AS i " +
            "WHERE i.order_id = o.id AND o.customer_id= :customerId AND i.creation_date BETWEEN :Edate AND :Fdate", nativeQuery = true)
    List<InvoiceModel> findAllByCustomerAndEdateAndFdate(@Param("customerId") Long customerId, @Param("Edate") LocalDate Edate, @Param("Fdate") LocalDate Fdate);

    @Query(value = "SELECT i.* FROM sc_business_management_platform.orders AS o, sc_business_management_platform.invoice AS i " +
            "WHERE i.order_id = o.id AND o.customer_id= :customerId AND i.creation_date >= :Edate", nativeQuery = true)
    List<InvoiceModel> findAllByCustomerAndEdate(@Param("customerId") Long customerId, @Param("Edate") LocalDate Edate);

    @Query(value = "SELECT i.* FROM sc_business_management_platform.invoice AS i " +
            "WHERE i.creation_date >= :Edate", nativeQuery = true)
    List<InvoiceModel> findAllByEdate(@Param("Edate") LocalDate Edate);

    @Query(value = "SELECT i.* FROM sc_business_management_platform.invoice AS i " +
            "WHERE i.creation_date BETWEEN :Edate AND :Fdate", nativeQuery = true)
    List<InvoiceModel> findAllByEdateAndFdate(@Param("Edate") LocalDate Edate, @Param("Fdate") LocalDate Fdate);

    List<InvoiceModel> findAllByOrder_Customer_id(Long customerId);

    List<InvoiceModel> findAllByInvoiceStatus_status(String orderStatus);
}
