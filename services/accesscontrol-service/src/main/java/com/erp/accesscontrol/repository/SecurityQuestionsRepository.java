package com.erp.accesscontrol.repository;

import com.erp.accesscontrol.model.SecurityQuestionsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecurityQuestionsRepository extends JpaRepository<SecurityQuestionsModel, Long> {
    Optional<List<SecurityQuestionsModel>> findAllByStatus_Id(Boolean status);
}
