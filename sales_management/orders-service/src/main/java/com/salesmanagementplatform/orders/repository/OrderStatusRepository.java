package com.salesmanagementplatform.orders.repository;

import com.salesmanagementplatform.orders.model.OrderStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusModel, Long> {
    OrderStatusModel findByStatus(String status);
}
