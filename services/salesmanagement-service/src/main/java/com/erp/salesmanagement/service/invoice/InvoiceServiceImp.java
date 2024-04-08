package com.erp.salesmanagement.service.invoice;

import com.erp.salesmanagement.error.exceptions.RequestException;
import com.erp.salesmanagement.model.invoice.InvoiceModel;
import com.erp.salesmanagement.model.invoice.InvoicePaymentMethodModel;
import com.erp.salesmanagement.model.invoice.InvoiceStatusModel;
import com.erp.salesmanagement.repository.invoice.InvoicePaymentMethodRepository;
import com.erp.salesmanagement.repository.invoice.InvoiceRepository;
import com.erp.salesmanagement.repository.invoice.InvoiceStatusRepository;
import com.erp.salesmanagement.service.order.OrderService;
import com.erp.salesmanagement.service.product.ProductStockService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceServiceImp implements InvoiceService{

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImp.class);

    private final InvoiceRepository invoiceRepository;
    private final InvoiceStatusRepository invoiceStatusRepository;
    private final InvoicePaymentMethodRepository invoicePaymentMethodRepository;
    private final ProductStockService productStockService;
    private final OrderService orderService;

    @Override
    public List<InvoiceModel> listAllByCustomerAndDate(FilterFields filterFields) {
        List<InvoiceModel> invoiceList = new ArrayList<InvoiceModel>();
        if(filterFields.customer() != null && filterFields.startDate() !=null && filterFields.finalDate() != null)
        {
            logger.info("Start search for all invoices");
            invoiceList = invoiceRepository.findAllByCustomerAndEdateAndFdate(filterFields.customer(),filterFields.startDate(),filterFields.finalDate().plusDays(1));
            if(invoiceList.isEmpty()) throw new RequestException("Validate that the client and dates are correct.","404-Not Found");
        }
        else if(filterFields.customer() != null && filterFields.startDate() !=null)
        {
            logger.info("Start search for all invoices");
            invoiceList = invoiceRepository.findAllByCustomerAndEdate(filterFields.customer(), filterFields.startDate());
            if(invoiceList.isEmpty()) throw new RequestException("Validate that the client and date are correct.","404-Not Found");
        }
        else if(filterFields.customer() != null)
        {
            logger.info("Start search for all invoices");
            invoiceList = invoiceRepository.findAllByOrder_Customer_id(filterFields.customer());
            if(invoiceList.isEmpty()) throw new RequestException("Customer not found with id " + filterFields.customer(),"404-Not Found");
        }
        else if(filterFields.startDate() !=null)
        {
            logger.info("Start search for all invoices");
            invoiceList = invoiceRepository.findAllByEdate(filterFields.startDate());
            if (invoiceList.isEmpty()) throw new RequestException("Start date is incorrect.","404-Not Found");
        }
        else if(filterFields.finalDate() != null) throw new RequestException("You cannot search for the invoice with only the final date.","400-Bad Request");
        else throw new RequestException("It is not possible to do a search with the data entered.","400-Bad Request");
        invoiceList.forEach(invoice ->
        {
            invoice.getOrder().setProductsJson(invoice.getOrder().convertStringToJsonNode(invoice.getOrder().getProducts()));
            invoice.getOrder().setOrderDetailsJson(invoice.getOrder().convertStringToJsonNode(invoice.getOrder().getOrderDetails()));
            invoice.getOrder().setProducts(null);
            invoice.getOrder().setOrderDetails(null);
        });
        return invoiceList;
    }

    @Override
    public List<InvoiceModel> listAllInvoices(String invoiceStatus) {
        logger.info("Start search for all invoices");
        List<InvoiceModel> invoiceList = invoiceRepository.findAll();
        if(invoiceList.isEmpty()) throw new RequestException("The invoice list is empty.","100-Continue");
        return invoiceList;
    }

    @Override
    public InvoiceModel listByInvoiceId(Long invoiceId) {
        logger.info("starting search by invoice id");
        InvoiceModel invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new RequestException("Invoice not found with id " + invoiceId,"404-Not Found"));
        invoice.getOrder().setProductsJson(invoice.getOrder().convertStringToJsonNode(invoice.getOrder().getProducts()));
        invoice.getOrder().setOrderDetailsJson(invoice.getOrder().convertStringToJsonNode(invoice.getOrder().getOrderDetails()));
        invoice.getOrder().setProducts(null);
        invoice.getOrder().setOrderDetails(null);
        return invoice;
    }

    @Override
    public void updateInvoice(InvoiceModel updateInvoice) {
        InvoiceModel invoice = invoiceRepository.findById(updateInvoice.getId()).orElseThrow(() -> new RequestException("Invoice not found with id " + updateInvoice.getId(),"404-Not Found"));
        if(invoice.getInvoiceStatus().getStatus().replaceAll(" ","").equalsIgnoreCase("pending"))
        {
            invoice.setExpirationDate(updateInvoice.getExpirationDate());
        }
        invoice.setPaid(updateInvoice.getPaid());
        invoice.addExchange();
        invoice.setPaymentMethod(updateInvoice.getPaymentMethod());
        invoice.setUpdateDate(LocalDateTime.now());
        logger.info("Start the modification of invoice");
        invoiceRepository.save(invoice);
    }

    @Override
    public void updateInvoiceStatus(Long invoiceId, String status) {
        InvoiceModel invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new RequestException("Invoice not found with id " + invoiceId,"404-Not Found"));
        InvoiceStatusModel invoiceStatus = new InvoiceStatusModel();
        if(!invoice.getInvoiceStatus().getStatus().equals("canceled"))
        {
            if(status.equals("paid"))
            {
                invoiceStatus = invoiceStatusRepository.findByStatus(status);
                if(invoiceStatus==null) throw new RequestException("Invoice status not found with status " + status,"404-Not Found");
            }
            else if(status.equals("pending"))
            {
                invoiceStatus = invoiceStatusRepository.findByStatus(status);
                if(invoiceStatus==null) throw new RequestException("Invoice status not found with status " + status,"404-Not Found");
            }
            else if(status.equals("canceled"))
            {
                invoiceStatus = invoiceStatusRepository.findByStatus(status);
                if(invoiceStatus==null) throw new RequestException("Invoice status not found with status " + status,"404-Not Found");
                invoice.getOrder().setProductsJson(invoice.getOrder().convertStringToJsonNode(invoice.getOrder().getProducts()));
                invoice.getOrder().convertJsonToProductList();
                productStockService.cancellationOfStockReduction(invoice.getOrder().getProductList());
            }
        }
        else throw new RequestException("You cannot change the status of a canceled invoice.","400-Bad Request");
        logger.info("Start the update of invoice status");
        invoice.setInvoiceStatus(invoiceStatus);
        invoiceRepository.save(invoice);
    }

    @Override
    public void saveInvoice(InvoiceModel invoice) {
        if (invoice.getId() == null)
        {
            InvoicePaymentMethodModel invoicePaymentMethod = invoicePaymentMethodRepository.findByPaymentMethod(invoice.getPaymentMethod().getPaymentMethod());
            if(invoicePaymentMethod==null) throw new RequestException("Invoice payment method not found: " + invoice.getInvoiceStatus().getStatus(),"404-Not Found");
            invoice.setPaymentMethod(invoicePaymentMethod);
            InvoiceStatusModel invoiceStatus = invoiceStatusRepository.findByStatus(invoice.getInvoiceStatus().getStatus());
            if(invoiceStatus==null) throw new RequestException("Invoice status not found with status " + invoice.getInvoiceStatus().getStatus(),"404-Not Found");
            invoice.setInvoiceStatus(invoiceStatus);
            orderService.listOrderById(invoice.getOrder().getId());
            invoice.addExchange();
            invoice.setCreationDate(LocalDateTime.now());
            invoice.setUpdateDate(null);
            if (invoice.getInvoiceStatus().getStatus().equals("pending") && !(invoice.getExpirationDate() == null)) {
                orderService.updateOrderStatus(invoice.getOrder().getId(), "delivered");
                invoiceRepository.save(invoice);
            } else if (invoice.getInvoiceStatus().getStatus().equals("pending") && invoice.getExpirationDate() == null) {
            throw new RequestException("If the invoice status is pending, you cannot leave the expiration date empty.", "400-Bad Request");
            } else if (!(invoice.getExpirationDate() == null) && !invoice.getInvoiceStatus().getStatus().equals("pending")) {
                throw new RequestException("There cannot be an expiration date if the invoice status is other than pending.", "400-Bad Request");
            } else {
                orderService.updateOrderStatus(invoice.getOrder().getId(), "delivered");
                invoiceRepository.save(invoice);
        }
    } else throw new RequestException("The invoice id must be null", "400-Bad Request");
    }
}
