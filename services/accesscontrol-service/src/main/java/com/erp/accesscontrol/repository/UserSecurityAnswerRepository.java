package com.erp.accesscontrol.repository;

import com.erp.accesscontrol.model.UserSecurityAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserSecurityAnswerRepository extends JpaRepository<UserSecurityAnswer, Long> {

    Optional<List<UserSecurityAnswer>> findAllByUser_Id(Long userId);
    Long countAllByUser_Id(Long userId);
}