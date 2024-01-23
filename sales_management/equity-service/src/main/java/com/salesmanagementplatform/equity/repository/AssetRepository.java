package com.salesmanagementplatform.equity.repository;

import com.salesmanagementplatform.equity.model.AssetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<AssetModel, Long> {

    List<AssetModel> findAllByReference(String reference);
    List<AssetModel> findAllByAssetType_AssetType(String assetType);
    List<AssetModel> findAllByAssetStatus_AssetStatus(String assetStatus);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_type at " +
            "ON a.asset_type = at.id " +
            "WHERE a.value >= :startValue " +
            "AND at.asset_type = :assetType", nativeQuery = true)
    List<AssetModel> findAllByStartValueAndType(@Param("startValue") float startValue, @Param("assetType") String assetType);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_status ass " +
            "ON a.asset_status = ass.id " +
            "WHERE a.value >= :startValue " +
            "AND ass.asset_status = :assetStatus", nativeQuery = true)
    List<AssetModel> findAllByStartValueAndStatus(@Param("startValue") float startValue, @Param("assetStatus") String assetStatus);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_type at " +
            "ON a.asset_type = at.id " +
            "WHERE a.value BETWEEN :startValue AND :finalValue " +
            "AND at.asset_type = :assetType", nativeQuery = true)
    List<AssetModel> findAllByValueAndType(@Param("startValue") float startValue, @Param("finalValue") float finalValue, @Param("assetType") String assetType);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_status ass " +
            "ON a.asset_status = ass.id " +
            "WHERE a.value BETWEEN :startValue AND :finalValue " +
            "AND ass.asset_status = :assetStatus", nativeQuery = true)
    List<AssetModel> findAllByValueAndStatus(@Param("startValue") float startValue, @Param("finalValue") float finalValue, @Param("assetStatus") String assetStatus);

    //Consultas de expiration date -----------------------------------------------------
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_type at " +
            "ON a.asset_type = at.id " +
            "WHERE a.expiration_date >= :startDate " +
            "AND at.asset_type = :assetType", nativeQuery = true)
    List<AssetModel> findAllByStartDateExpirationAndType(@Param("startDate") LocalDate startDate, @Param("assetType") String assetType);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_type ass " +
            "ON a.asset_status = ass.id " +
            "WHERE a.expiration_date >= :startDate " +
            "AND ass.asset_status = :assetStatus", nativeQuery = true)
    List<AssetModel> findAllByStartDateExpirationAndStatus(@Param("startDate") LocalDate startDate, @Param("assetStatus") String assetStatus);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_type at " +
            "ON a.asset_type = at.id " +
            "WHERE a.expiration_date BETWEEN :startDate AND :finalDate " +
            "AND at.asset_type = :assetType", nativeQuery = true)
    List<AssetModel> findAllByDateExpirationAndType(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("assetType") String assetType);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_type ass " +
            "ON a.asset_status = ass.id " +
            "WHERE a.expiration_date BETWEEN :startDate AND :finalDate " +
            "AND ass.asset_status = :assetStatus", nativeQuery = true)
    List<AssetModel> findAllByDateExpirationAndStatus(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("assetStatus") String assetStatus);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "WHERE a.expiration_date >= :startDate " +
            "AND a.reference = :reference", nativeQuery = true)
    List<AssetModel> findAllByStartDateExpirationAndReference(@Param("startDate") LocalDate startDate, @Param("reference") String reference);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "WHERE a.expiration_date BETWEEN :startDate AND :finalDate" +
            "AND a.reference = :reference", nativeQuery = true)
    List<AssetModel> findAllByDateExpirationAndReference(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("reference") String reference);

    //Consultas de creation date -----------------------------------------------------
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_type at " +
            "ON a.asset_type = at.id " +
            "WHERE a.creation_date >= :startDate " +
            "AND at.asset_type = :assetType", nativeQuery = true)
    List<AssetModel> findAllByStartDateCreationAndType(@Param("startDate") LocalDate startDate, @Param("assetType") String assetType);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_type ass " +
            "ON a.asset_status = ass.id " +
            "WHERE a.creation_date >= :startDate " +
            "AND ass.asset_status = :assetStatus", nativeQuery = true)
    List<AssetModel> findAllByStartDateCreationAndStatus(@Param("startDate") LocalDate startDate, @Param("assetStatus") String assetStatus);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_type at " +
            "ON a.asset_type = at.id " +
            "WHERE a.creation_date BETWEEN :startDate AND :finalDate " +
            "AND at.asset_type = :assetType", nativeQuery = true)
    List<AssetModel> findAllByDateCreationAndType(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("assetType") String assetType);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "JOIN sc_business_management_platform.asset_type ass " +
            "ON a.asset_status = ass.id " +
            "WHERE a.creation_date BETWEEN :startDate AND :finalDate " +
            "AND ass.asset_status = :assetStatus", nativeQuery = true)
    List<AssetModel> findAllByDateCreationAndStatus(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("assetStatus") String assetStatus);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "WHERE a.creation_date >= :startDate " +
            "AND a.reference = :reference", nativeQuery = true)
    List<AssetModel> findAllByStartDateCreationAndReference(@Param("startDate") LocalDate startDate, @Param("reference") String reference);
    @Query(value = "SELECT a.* " +
            "FROM sc_business_management_platform.asset a " +
            "WHERE a.creation_date BETWEEN :startDate AND :finalDate" +
            "AND a.reference = :reference", nativeQuery = true)
    List<AssetModel> findAllByDateCreationAndReference(@Param("startDate") LocalDate startDate, @Param("finalDate") LocalDate finalDate, @Param("reference") String reference);
}
