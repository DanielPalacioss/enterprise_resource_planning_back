package com.erp.salesmanagement.controller.invoice;

import com.erp.salesmanagement.error.DataValidation;
import com.erp.salesmanagement.model.invoice.InvoiceModel;
import com.erp.salesmanagement.service.invoice.FilterFields;
import com.erp.salesmanagement.service.invoice.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sm/invoices")
@CrossOrigin(origins = "http://localhost:8082")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    DataValidation dataValidation = new DataValidation();

    @GetMapping("/listByCustomerAndDate")
    public ResponseEntity<List<InvoiceModel>> getAllByCustomerAndDate(@RequestBody FilterFields filterFields) {
        return ResponseEntity.ok(invoiceService.listAllByCustomerAndDate(filterFields));
    }

    @GetMapping("/listByStatus")
    public ResponseEntity<List<InvoiceModel>> listAllInvoices(@RequestParam String status) {
        return ResponseEntity.ok(invoiceService.listAllInvoices(status));
    }

    @GetMapping("/listById")
    public ResponseEntity<InvoiceModel> listByInvoiceId(@RequestParam Long invoiceId) {
        return ResponseEntity.ok(invoiceService.listByInvoiceId(invoiceId));
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
