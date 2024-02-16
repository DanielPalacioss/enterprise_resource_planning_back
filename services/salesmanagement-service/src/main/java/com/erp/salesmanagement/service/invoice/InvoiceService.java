package com.erp.salesmanagement.service.invoice;

import com.erp.salesmanagement.model.invoice.InvoiceModel;

import java.util.List;

public interface InvoiceService {

    List<InvoiceModel> listAllByCustomerAndDate(FilterFields filterFields);

    List<InvoiceModel> listAllInvoices(String invoiceStatus);

    InvoiceModel listByInvoiceId(Long invoiceId);

    void updateInvoice(InvoiceModel updateInvoice);

    void updateInvoiceStatus(Long invoiceId, String status);

    void saveInvoice(InvoiceModel invoice);
}
