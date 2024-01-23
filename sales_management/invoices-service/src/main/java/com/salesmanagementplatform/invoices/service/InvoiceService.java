package com.salesmanagementplatform.invoices.service;

import com.salesmanagementplatform.invoices.model.InvoiceModel;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {

    List<InvoiceModel> listAllByCustomerAndDate(FilterFields filterFields);

    List<InvoiceModel> listAllInvoices(String invoiceStatus);

    InvoiceModel listByInvoiceId(Long invoiceId);

    void updateInvoice(InvoiceModel updateInvoice);

    void updateInvoiceStatus(Long invoiceId, String status);

    void saveInvoice(InvoiceModel invoice);
}
