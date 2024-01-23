package com.salesmanagementplatform.equity.repository;

import com.salesmanagementplatform.equity.model.AssetModel;
import com.salesmanagementplatform.equity.model.LiabilityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LiabilityRepository extends JpaRepository<LiabilityModel, Long> {
    List<LiabilityModel> findAllByReference(String reference);
    List<LiabilityModel> findAllByLiabilityType_LiabilityType(String liabilityType);
    List<LiabilityModel> findAllByLiabilityStatus_LiabilityStatus(String liabilityStatus);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_type lt " +
            "ON l.liability_type = lt.id " +
            "WHERE l.value >= :startValue " +
            "AND lt.liability_type = :liabilityType", nativeQuery = true)
    List<LiabilityModel> findAllByStartValueAndType(@Param("startValue") float startValue, @Param("liabilityType") String liabilityType);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_status ls " +
            "ON l.liability_status = ls.id " +
            "WHERE l.value >= :startValue " +
            "AND ls.liability_status = :liabilityStatus", nativeQuery = true)
    List<LiabilityModel> findAllByStartValueAndStatus(@Param("startValue") float startValue, @Param("liabilityStatus") String liabilityStatus);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_type lt " +
            "ON l.liability_type = lt.id " +
            "WHERE l.value BETWEEN :startValue AND :finalValue " +
            "AND lt.liability_type = :liabilityType", nativeQuery = true)
    List<LiabilityModel> findAllByValueAndType(@Param("startValue") float startValue, @Param("finalValue") float finalValue, @Param("liabilityType") String liabilityType);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_status ls " +
            "ON l.liability_status = ls.id " +
            "WHERE l.value BETWEEN :startValue AND :finalValue " +
            "AND ls.liability_status = :liabilityStatus", nativeQuery = true)
    List<LiabilityModel> findAllByValueAndStatus(@Param("startValue") float startValue, @Param("finalValue") float finalValue, @Param("liabilityStatus") String liabilityStatus);

    //Consultas de expiration date -----------------------------------------------------
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_type lt " +
            "ON l.liability_type = lt.id " +
            "WHERE l.expiration_date >= :startDate " +
            "AND lt.liability_type = :liabilityType", nativeQuery = true)
    List<LiabilityModel> findAllByStartDateExpirationAndType(@Param("startDate") LocalDate startDate, @Param("liabilityType") String liabilityType);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_status ls " +
            "ON l.liability_status = ls.id " +
            "WHERE l.expiration_date >= :startDate " +
            "AND ls.liability_status = :liabilityStatus", nativeQuery = true)
    List<LiabilityModel> findAllByStartDateExpirationAndStatus(@Param("startDate") LocalDate startDate, @Param("liabilityStatus") String liabilityStatus);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_type lt " +
            "ON l.liability_type = lt.id " +
            "WHERE l.expiration_date BETWEEN :startDate AND :finalDate " +
            "AND lt.liability_type = :liabilityType", nativeQuery = true)
    List<LiabilityModel> findAllByDateExpirationAndType(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("liabilityType") String liabilityType);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_status ls " +
            "ON l.liability_status = ls.id " +
            "WHERE l.expiration_date BETWEEN :startDate AND :finalDate " +
            "AND ls.liability_status = :liabilityStatus", nativeQuery = true)
    List<LiabilityModel> findAllByDateExpirationAndStatus(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("liabilityStatus") String liabilityStatus);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "WHERE a.expiration_date >= :startDate " +
            "AND a.reference = :reference", nativeQuery = true)
    List<LiabilityModel> findAllByStartDateExpirationAndReference(@Param("startDate") LocalDate startDate, @Param("reference") String reference);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "WHERE l.expiration_date BETWEEN :startDate AND :finalDate" +
            "AND l.reference = :reference", nativeQuery = true)
    List<LiabilityModel> findAllByDateExpirationAndReference(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("reference") String reference);

    //Consultas de creation date -----------------------------------------------------
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_type lt " +
            "ON l.liability_type = lt.id " +
            "WHERE l.creation_date >= :startDate " +
            "AND lt.liability_type = :liabilityType", nativeQuery = true)
    List<LiabilityModel> findAllByStartDateCreationAndType(@Param("startDate") LocalDate startDate, @Param("liabilityType") String liabilityType);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_status ls " +
            "ON l.liability_status = ls.id " +
            "WHERE l.creation_date >= :startDate " +
            "AND ls.liability_status = :liabilityStatus", nativeQuery = true)
    List<LiabilityModel> findAllByStartDateCreationAndStatus(@Param("startDate") LocalDate startDate, @Param("liabilityStatus") String liabilityStatus);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_type lt " +
            "ON l.liability_type = lt.id " +
            "WHERE l.creation_date BETWEEN :startDate AND :finalDate " +
            "AND lt.liability_type = :liabilityType", nativeQuery = true)
    List<LiabilityModel> findAllByDateCreationAndType(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("liabilityType") String liabilityType);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "JOIN sc_business_management_platform.liability_status ls " +
            "ON l.liability_status = ls.id " +
            "WHERE l.creation_date BETWEEN :startDate AND :finalDate " +
            "AND ls.liability_status = :liabilityStatus", nativeQuery = true)
    List<LiabilityModel> findAllByDateCreationAndStatus(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("liabilityStatus") String liabilityStatus);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "WHERE l.creation_date >= :startDate " +
            "AND l.reference = :reference", nativeQuery = true)
    List<LiabilityModel> findAllByStartDateCreationAndReference(@Param("startDate") LocalDate startDate, @Param("reference") String reference);
    @Query(value = "SELECT l.* " +
            "FROM sc_business_management_platform.liability l " +
            "WHERE l.creation_date BETWEEN :startDate AND :finalDate" +
            "AND l.reference = :reference", nativeQuery = true)
    List<LiabilityModel> findAllByDateCreationAndReference(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("reference") String reference);
}
