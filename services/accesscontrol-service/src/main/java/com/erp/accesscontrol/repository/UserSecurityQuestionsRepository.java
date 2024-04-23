package com.erp.accesscontrol.repository;

import com.erp.accesscontrol.model.UserSecurityQuestionsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserSecurityQuestionsRepository extends JpaRepository<UserSecurityQuestionsModel, Long> {
    Optional<UserSecurityQuestionsModel> findByUser_Id(Long userId);
}
