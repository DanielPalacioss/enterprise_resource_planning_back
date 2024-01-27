package com.salesmanagementplatform.map.service;

import com.salesmanagementplatform.map.error.exceptions.RequestException;
import com.salesmanagementplatform.map.model.DepartmentModel;
import com.salesmanagementplatform.map.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImp implements DepartmentService{

    private final Logger logger = LoggerFactory.getLogger(DepartmentServiceImp.class);
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImp(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentModel> listAllDepartment() {
        logger.info("Start search for all department");
        List<DepartmentModel> departmentList = departmentRepository.findAll();
        if (departmentList.isEmpty()) throw new RequestException("The department list is empty.","100-Continue");
        return departmentList;
    }
}
