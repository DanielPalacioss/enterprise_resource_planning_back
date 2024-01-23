package com.salesmanagementplatform.invoices.service;

import com.salesmanagementplatform.invoices.model.InvoicePaymentMethodModel;

import java.util.List;

public interface InvoicePaymentMethodService {

    List<InvoicePaymentMethodModel> listAllInvoicePaymentMethod(String status);

    void updateInvoicePaymentMethod(InvoicePaymentMethodModel updatePaymentMethod);

    void deleteInvoicePaymentMethod(Long invoicePaymentMethodId);

    void saveInvoicePaymentMethod(InvoicePaymentMethodModel paymentMethod);
}
