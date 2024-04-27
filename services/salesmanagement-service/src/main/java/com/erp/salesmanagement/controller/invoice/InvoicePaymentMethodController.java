package com.erp.salesmanagement.controller.invoice;

import com.erp.salesmanagement.error.DataValidation;
import com.erp.salesmanagement.model.invoice.InvoicePaymentMethodModel;
import com.erp.salesmanagement.service.invoice.InvoicePaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sm/invoicesPaymentMethod")
@CrossOrigin(origins = "http://localhost:8082")
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

    @DeleteMapping("{invoicePaymentMethodId}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable Long invoicePaymentMethodId)
    {
        invoicePaymentMethodService.deleteInvoicePaymentMethod(invoicePaymentMethodId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The invoice payment method has been successfully deleted.");
    }
}
