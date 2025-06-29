package com.erp.salesmanagement.service.invoice;

import com.erp.salesmanagement.error.exceptions.RequestException;
import com.erp.salesmanagement.model.Status;
import com.erp.salesmanagement.model.invoice.InvoicePaymentMethodModel;
import com.erp.salesmanagement.repository.StatusRepository;
import com.erp.salesmanagement.repository.invoice.InvoicePaymentMethodRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InvoicePaymentMethodServiceImp implements InvoicePaymentMethodService{

    private static final Logger logger = LoggerFactory.getLogger(InvoicePaymentMethodServiceImp.class);
    private final InvoicePaymentMethodRepository paymentMethodRepository;
    private final StatusRepository statusRepository;

    @Override
    public List<InvoicePaymentMethodModel> listAllInvoicePaymentMethod(String status) {
        logger.info("Start search for all payment method");
        List<InvoicePaymentMethodModel> paymentMethodList = new ArrayList<InvoicePaymentMethodModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("active")) {
            paymentMethodList= paymentMethodRepository.findAllByStatus_Id(true).orElseThrow(() -> new RequestException("La lista de payment method en estado '"+status+"' está vacía","100-Continue"));
        }
        else if (status.replaceAll(" ","").equalsIgnoreCase("inactive")) {
            paymentMethodList= paymentMethodRepository.findAllByStatus_Id(false).orElseThrow(() -> new RequestException("La lista de payment method en estado '"+status+"' está vacía","100-Continue"));
        }
        else throw new RequestException("No existe el estado: '"+status+"' en la payment method","100-Continue");
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
    public void deleteInvoicePaymentMethod(Long invoicePaymentMethodId)
    {
        InvoicePaymentMethodModel paymentMethod = paymentMethodRepository.findById(invoicePaymentMethodId).orElseThrow(()-> new RequestException("Payment Method not found with id " + invoicePaymentMethodId, "404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        paymentMethod.setStatus(status);
        paymentMethod.setUpdateDate(LocalDateTime.now());
        logger.info("Start deleting payment method");
        paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public void saveInvoicePaymentMethod(InvoicePaymentMethodModel paymentMethod) {
        if(statusRepository.count() < 1) throw new RequestException("No status created","404-Not Found");
        if (paymentMethod.getId() == null)
        {
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
            paymentMethod.setStatus(status);
            paymentMethod.setCreationDate(LocalDateTime.now());
            paymentMethod.setUpdateDate(null);
            logger.info("Start the creation of payment method");
            paymentMethodRepository.save(paymentMethod);
        }
        else throw new RequestException("The payment method id must be null", "400-Bad Request");
    }
}
