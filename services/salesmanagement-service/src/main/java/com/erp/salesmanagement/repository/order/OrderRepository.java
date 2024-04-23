package com.erp.salesmanagement.repository.order;

import com.erp.salesmanagement.model.order.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    @Query(value = "SELECT o.* FROM sc_business_management_platform.orders AS o " +
            "JOIN sc_business_management_platform.order_status AS os ON o.order_status = os.id " +
            "WHERE os.status IN ('preparing', 'ontheway')", nativeQuery = true)
    Optional<List<OrderModel>> findAllByPreparingAndOntheway();
    Optional<List<OrderModel>> findAllByOrderStatus_status(String status);
}
