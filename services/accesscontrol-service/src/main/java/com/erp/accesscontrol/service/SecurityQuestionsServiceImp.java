package com.erp.accesscontrol.service;

import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.model.SecurityQuestionsModel;
import com.erp.accesscontrol.model.Status;
import com.erp.accesscontrol.repository.SecurityQuestionsRepository;
import com.erp.accesscontrol.repository.StatusRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SecurityQuestionsServiceImp  implements  SecurityQuestionsService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    private final SecurityQuestionsRepository securityQuestionsRepository;
    private final StatusRepository statusRepository;

    @Override
    public List<SecurityQuestionsModel> getAllSecurityQuestions(String status) {
        logger.info("Start search for all security questions");
        List<SecurityQuestionsModel> securityQuestionsList = new ArrayList<SecurityQuestionsModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("active")) securityQuestionsList= securityQuestionsRepository.findAllByStatus_Id(true).orElseThrow(() -> new RequestException("La lista de security questions en estado '"+status+"' está vacía","100-Continue"));
        else if (status.replaceAll(" ","").equalsIgnoreCase("inactive")) securityQuestionsList= securityQuestionsRepository.findAllByStatus_Id(false).orElseThrow(() -> new RequestException("La lista de security questions en estado '"+status+"' está vacía","100-Continue"));
        else throw new RequestException("No existe el estado: '"+status+"'","100-Continue");
        return securityQuestionsList;
    }

    @Override
    public void updateSecurityQuestions(SecurityQuestionsModel updateSecurityQuestions) {
        SecurityQuestionsModel securityQuestions = securityQuestionsRepository.findById(updateSecurityQuestions.getId()).orElseThrow(() -> new RequestException("Security questions not found","404-Not Found"));
        securityQuestions.setQuestion(updateSecurityQuestions.getQuestion());
        securityQuestions.setUpdateDate(LocalDateTime.now());
        logger.info("Start the update of security questions");
        securityQuestionsRepository.save(securityQuestions);
    }

    @Override
    public void saveSecurityQuestions(SecurityQuestionsModel securityQuestions) {
        if(statusRepository.count() < 1) throw new RequestException("No status created","404-Not Found");
        if(securityQuestions.getId()==null)
        {
            securityQuestions.setCreationDate(LocalDateTime.now());
            securityQuestions.setUpdateDate(null);
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
            securityQuestions.setStatus(status);
            logger.info("Start the creation of security questions");
            securityQuestionsRepository.save(securityQuestions);
        }
        else throw new RequestException("The security questions id must be null","400-Bad Request");
    }

    @Override
    public void deleteSecurityQuestions(Long securityQuestionsId) {
        SecurityQuestionsModel securityQuestions = securityQuestionsRepository.findById(securityQuestionsId).orElseThrow(() -> new RequestException("Security questions not found","404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        securityQuestions.setStatus(status);
        logger.info("Start deleting security questions");
        securityQuestionsRepository.save(securityQuestions);
    }
}
