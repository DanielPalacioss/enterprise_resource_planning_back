package com.erp.salesmanagement.service.customer;

import com.erp.salesmanagement.error.exceptions.RequestException;
import com.erp.salesmanagement.model.Status;
import com.erp.salesmanagement.model.customer.CustomerCategoryModel;
import com.erp.salesmanagement.repository.StatusRepository;
import com.erp.salesmanagement.repository.customer.CustomerCategoryRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerCategoryServiceImp implements CustomerCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCategoryServiceImp.class);
    private final CustomerCategoryRepository customerCategoryRepository;
    private final StatusRepository statusRepository;

    @Override
    public List<CustomerCategoryModel> listOfAllCustomersCategory(String status) {
        logger.info("Start search for all customer category");
        List<CustomerCategoryModel> customerCategoryList= new ArrayList<CustomerCategoryModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("active")) {
            customerCategoryList= customerCategoryRepository.findAllByStatus_Id(true).orElseThrow(() -> new RequestException("La lista de categoria de clientes en estado '"+status+"' está vacía","100-Continue"));
        }
        else if (status.replaceAll(" ","").equalsIgnoreCase("inactive")) {
            customerCategoryList= customerCategoryRepository.findAllByStatus_Id(false).orElseThrow(() -> new RequestException("La lista de categoria de clientes en estado '"+status+"' está vacía","100-Continue"));
        }
        else throw new RequestException("No existe el estado: '"+status+"' en la categoria de clientes","100-Continue");
        return customerCategoryList;
    }

    @Override
    public void updateCustomerCategory(CustomerCategoryModel updatedCustomerCategory) {
        CustomerCategoryModel customerCategoryModel = customerCategoryRepository.findById(updatedCustomerCategory.getId()).orElseThrow(() -> new RequestException("Customer category not found with id " + updatedCustomerCategory.getId(),"404-Not Found"));
        customerCategoryModel.setCategory(updatedCustomerCategory.getCategory());
        customerCategoryModel.setDescription(updatedCustomerCategory.getDescription());
        customerCategoryModel.setUpdateDate(LocalDateTime.now());
        logger.info("Start the modification of customer category");
        customerCategoryRepository.save(customerCategoryModel);
    }

    @Override
    public void deleteCustomerCategory(Long customerCategoryId) {
        CustomerCategoryModel customerCategoryModel = customerCategoryRepository.findById(customerCategoryId).orElseThrow(() -> new RequestException("Customer category not found with id " + customerCategoryId,"404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        customerCategoryModel.setStatus(status);
        customerCategoryModel.setUpdateDate(LocalDateTime.now());
        logger.info("Start deleting customer category");
        customerCategoryRepository.save(customerCategoryModel);
    }

    @Override
    public void saveCustomerCategory(CustomerCategoryModel customerCategoryModel) {
        if(statusRepository.count() < 1) throw new RequestException("No status created","404-Not Found");
        if(customerCategoryModel.getId()==null) {
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
            customerCategoryModel.setStatus(status);
            customerCategoryModel.setCreationDate(LocalDateTime.now());
            customerCategoryModel.setUpdateDate(null);
            logger.info("Start the creation of customer category");
            customerCategoryRepository.save(customerCategoryModel);
        }
        else throw new RequestException("The customer category id must be null", "400-Bad Request");
    }

}
