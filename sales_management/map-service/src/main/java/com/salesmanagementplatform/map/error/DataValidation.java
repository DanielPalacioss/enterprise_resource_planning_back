package com.salesmanagementplatform.map.error;


import com.salesmanagementplatform.map.error.exceptions.RequestException;
import org.springframework.validation.BindingResult;

import java.util.Objects;

public class DataValidation {
    public void handleValidationError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new RequestException(errorMessage, "400-bad request");
        }
    }
}
