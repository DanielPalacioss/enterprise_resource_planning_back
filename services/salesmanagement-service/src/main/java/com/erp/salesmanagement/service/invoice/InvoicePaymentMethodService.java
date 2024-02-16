package com.erp.salesmanagement.service.invoice;

import com.erp.salesmanagement.model.invoice.InvoicePaymentMethodModel;

import java.util.List;

public interface InvoicePaymentMethodService {

    List<InvoicePaymentMethodModel> listAllInvoicePaymentMethod(String status);

    void updateInvoicePaymentMethod(InvoicePaymentMethodModel updatePaymentMethod);

    void deleteInvoicePaymentMethod(Long invoicePaymentMethodId);

    void saveInvoicePaymentMethod(InvoicePaymentMethodModel paymentMethod);
}
