package com.erp.salesmanagement.repository.order;

import com.erp.salesmanagement.model.order.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {}
