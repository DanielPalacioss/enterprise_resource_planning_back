package com.erp.salesmanagement.error;

import com.erp.salesmanagement.error.exceptions.RequestException;
import org.springframework.validation.BindingResult;

import java.util.Objects;

public class DataValidation {
    public void handleValidationError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new RequestException(errorMessage, "400");
        }
    }
}
