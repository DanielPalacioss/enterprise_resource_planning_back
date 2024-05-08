package com.erp.purchasingmanagement.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private String code;
    private String message;
}
