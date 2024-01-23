package com.salesmanagementplatform.invoices.controller;

import com.salesmanagementplatform.invoices.error.DataValidation;
import com.salesmanagementplatform.invoices.model.InvoicePaymentMethodModel;
import com.salesmanagementplatform.invoices.service.InvoicePaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/invoicesPaymentMethod")
@CrossOrigin(origins = "http://localhost:8084")
public class InvoicePaymentMethodController {

    @Autowired
    InvoicePaymentMethodService invoicePaymentMethodService;

    DataValidation dataValidation = new DataValidation();

    @GetMapping()
    public ResponseEntity<List<InvoicePaymentMethodModel>> getAllInvoicePaymentMethod(@RequestParam String status) {
        return ResponseEntity.ok(invoicePaymentMethodService.listAllInvoicePaymentMethod(status));
    }

    @PostMapping
    public ResponseEntity<?> saveInvoicePaymentMethod(@Valid @RequestBody InvoicePaymentMethodModel paymentMethodModel, BindingResult bindingResult) {
        dataValidation.handleValidationError(bindingResult);
        invoicePaymentMethodService.saveInvoicePaymentMethod(paymentMethodModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The invoice payment method has been created successfully.");
    }

    @PutMapping
    public ResponseEntity<?> updatePaymentMethod(@Valid @RequestBody InvoicePaymentMethodModel paymentMethodModel, BindingResult bindingResult){
        dataValidation.handleValidationError(bindingResult);
        invoicePaymentMethodService.updateInvoicePaymentMethod(paymentMethodModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The invoice payment method has been successfully modified.");
    }

    @DeleteMapping
    public ResponseEntity<?> deletePaymentMethod(@RequestParam Long invoicePaymentMethodId)
    {
        invoicePaymentMethodService.deleteInvoicePaymentMethod(invoicePaymentMethodId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The invoice payment method has been successfully deleted.");
    }
}
