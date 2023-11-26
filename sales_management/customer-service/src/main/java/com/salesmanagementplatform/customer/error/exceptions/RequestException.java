package com.salesmanagementplatform.customer.error.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class RequestException extends RuntimeException{
    private String code;

    public RequestException(String message, String code) {
        super(message);
        this.code = code;
    }
}
