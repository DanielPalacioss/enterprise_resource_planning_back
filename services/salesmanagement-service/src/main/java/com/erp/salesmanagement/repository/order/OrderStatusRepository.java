package com.erp.salesmanagement.repository.order;

import com.erp.salesmanagement.model.order.OrderStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusModel, Long> {
    OrderStatusModel findByStatus(String status);
}
