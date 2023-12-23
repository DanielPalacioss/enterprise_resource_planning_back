package com.salesmanagementplatform.invoices.service;

import com.salesmanagementplatform.invoices.model.InvoicePaymentMethodModel;

import java.util.List;

public interface InvoicePaymentMethodService {

    List<InvoicePaymentMethodModel> listAllInvoicePaymentMethod();

    void updateInvoicePaymentMethod(InvoicePaymentMethodModel updatePaymentMethod);

    void saveInvoicePaymentMethod(InvoicePaymentMethodModel paymentMethod);
}
