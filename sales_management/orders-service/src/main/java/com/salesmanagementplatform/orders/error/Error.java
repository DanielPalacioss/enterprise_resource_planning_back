package com.salesmanagementplatform.orders.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private String message;
    private String code;
}
