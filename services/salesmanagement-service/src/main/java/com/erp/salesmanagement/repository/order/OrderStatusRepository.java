package com.erp.salesmanagement.repository.order;

import com.erp.salesmanagement.model.order.OrderStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusModel, Long> {
    Optional<OrderStatusModel> findByStatus(String status);
}
