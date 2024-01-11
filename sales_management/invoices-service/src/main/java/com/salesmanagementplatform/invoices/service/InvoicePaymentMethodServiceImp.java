package com.salesmanagementplatform.invoices.service;

import com.salesmanagementplatform.invoices.error.exceptions.RequestException;
import com.salesmanagementplatform.invoices.model.InvoicePaymentMethodModel;
import com.salesmanagementplatform.invoices.repository.InvoicePaymentMethodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoicePaymentMethodServiceImp implements InvoicePaymentMethodService{

    private static final Logger logger = LoggerFactory.getLogger(InvoicePaymentMethodServiceImp.class);
    private final InvoicePaymentMethodRepository paymentMethodRepository;

    public InvoicePaymentMethodServiceImp(InvoicePaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public List<InvoicePaymentMethodModel> listAllInvoicePaymentMethod() {
        logger.info("Start search for all payment method");
        List<InvoicePaymentMethodModel> paymentMethodList = paymentMethodRepository.findAll();
        if (paymentMethodList.isEmpty())
        {
            throw new RequestException("La lista de metodo de pagos esta vacia","100-Continue");
        }
        return paymentMethodList;
    }

    @Override
    public void updateInvoicePaymentMethod(InvoicePaymentMethodModel updatePaymentMethod) {
        InvoicePaymentMethodModel paymentMethod = paymentMethodRepository.findById(updatePaymentMethod.getId()).orElseThrow(()-> new RequestException("Payment Method not found with id " + updatePaymentMethod.getId(), "404-Not Found"));
        paymentMethod.setPaymentMethod(updatePaymentMethod.getPaymentMethod());
        paymentMethod.setDescription(updatePaymentMethod.getDescription());
        paymentMethod.setUpdateDate(LocalDateTime.now());
        logger.info("Start the update of payment method");
        paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public void saveInvoicePaymentMethod(InvoicePaymentMethodModel paymentMethod) {
        if (paymentMethod.getId() == null)
        {
            paymentMethod.setCreationDate(LocalDateTime.now());
            paymentMethod.setUpdateDate(null);
            logger.info("Start the creation of payment method");
            paymentMethodRepository.save(paymentMethod);
        }
        else throw new RequestException("The payment method id must be null", "400-Bad Request");
    }
}
