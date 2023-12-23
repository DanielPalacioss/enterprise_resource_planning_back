package com.salesmanagementplatform.invoices.controller;

import com.salesmanagementplatform.invoices.error.DataValidation;
import com.salesmanagementplatform.invoices.model.InvoiceModel;
import com.salesmanagementplatform.invoices.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/invoices")
@CrossOrigin(origins = "http://localhost:8084")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    DataValidation dataValidation = new DataValidation();

    @GetMapping("listByCustomerAndDate/")
    public ResponseEntity<List<InvoiceModel>> getAllByCustomerAndDate(@PathVariable Long customer, @PathVariable LocalDate startDate, @PathVariable LocalDate finalDate) {
        return ResponseEntity.ok(invoiceService.listAllByCustomerAndDate(customer, startDate, finalDate));
    }

    @GetMapping("listByStatus/")
    public ResponseEntity<List<InvoiceModel>> listAllInvoices(@PathVariable String status) {
        return ResponseEntity.ok(invoiceService.listAllInvoices(status.replaceAll(" ","").toLowerCase()));
    }

    @GetMapping("listById/")
    public ResponseEntity<InvoiceModel> listByInvoiceId(@PathVariable Long customer) {
        return ResponseEntity.ok(invoiceService.listByInvoiceId(customer));
    }

    @PostMapping
    public ResponseEntity<?> saveInvoice(@Valid @RequestBody InvoiceModel invoiceModel, BindingResult bindingResult){
        dataValidation.handleValidationError(bindingResult);
        invoiceService.saveInvoice(invoiceModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The invoice has been created successfully.");
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateInvoiceStatus(@RequestParam Long invoiceId, @RequestParam String status)
    {
        invoiceService.updateInvoiceStatus(invoiceId,status.replaceAll(" ","").toLowerCase());
        return ResponseEntity.status(HttpStatus.CREATED).body("Invoice status updated successfully.");
    }

    @PutMapping
    public ResponseEntity<?> updateInvoice(@Valid @RequestBody InvoiceModel invoiceModel, BindingResult bindingResult){
        dataValidation.handleValidationError(bindingResult);
        invoiceService.updateInvoice(invoiceModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The invoice has been successfully modified.");
    }
}
