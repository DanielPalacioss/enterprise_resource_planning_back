package com.salesmanagementplatform.equity.service;

import com.salesmanagementplatform.equity.error.exceptions.RequestException;
import com.salesmanagementplatform.equity.model.AssetModel;
import com.salesmanagementplatform.equity.model.LiabilityModel;
import com.salesmanagementplatform.equity.repository.AssetRepository;
import com.salesmanagementplatform.equity.repository.LiabilityRepository;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record FilterFields(@NotEmpty String reference, float startValue, float finalValue, @NotEmpty String type, @NotEmpty String status, LocalDate startDate, LocalDate finalDate, String typeDate) {
        private static AssetRepository assetRepository;
        private static LiabilityRepository liabilityRepository;

        List<AssetModel> getAllAsset()
        {
                if((reference != null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status ==null) && (startDate == null)
                        && (finalDate == null))
                {
                        return assetRepository.findAllByReference(reference);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate == null)
                        && (finalDate == null))
                {
                        return assetRepository.findAllByAssetType_AssetType(type);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status !=null) && (startDate == null)
                        && (finalDate == null))
                {
                        return assetRepository.findAllByAssetStatus_AssetStatus(status);
                }
                else if((reference == null) && (startValue > 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate == null)
                        && (finalDate == null))
                {
                        return assetRepository.findAllByStartValueAndType(startValue, type);
                }
                else if((reference == null) && (startValue > 0) && (finalValue == 0) && (type ==null) && (status !=null) && (startDate == null)
                        && (finalDate == null))
                {
                        return assetRepository.findAllByStartValueAndStatus(startValue, status);
                }
                else if((reference == null) && (startValue > 0) && (finalValue > 0) && (type !=null) && (status ==null) && (startDate == null)
                        && (finalDate() == null))
                {
                        return  assetRepository.findAllByValueAndType(startValue, finalValue, type);
                }
                else if((reference == null) && (startValue > 0) && (finalValue > 0) && (type ==null) && (status !=null) && (startDate == null)
                        && (finalDate == null))
                {
                        return assetRepository.findAllByValueAndStatus(startValue, finalValue, status);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("expiration")))
                {
                        return assetRepository.findAllByStartDateExpirationAndType(startDate, type);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("creation")))
                {
                        return assetRepository.findAllByStartDateCreationAndType(startDate, type);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status !=null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("expiration")))
                {
                        return  assetRepository.findAllByStartDateExpirationAndStatus(startDate, status);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status !=null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("creation")))
                {
                        return  assetRepository.findAllByStartDateCreationAndStatus(startDate, status);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("expiration")))
                {
                        return  assetRepository.findAllByDateExpirationAndType(startDate, finalDate, type);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("creation")))
                {
                        return  assetRepository.findAllByDateCreationAndType(startDate, finalDate, type);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status !=null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("expiration")))
                {
                        return assetRepository.findAllByDateExpirationAndStatus(startDate, finalDate, status);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status !=null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("creation")))
                {
                        return assetRepository.findAllByDateCreationAndStatus(startDate, finalDate, status);
                }
                else if((reference != null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status ==null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("expiration")))
                {
                        return assetRepository.findAllByStartDateExpirationAndReference(startDate, reference);
                }
                else if((reference != null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status ==null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("creation")))
                {
                        return assetRepository.findAllByStartDateCreationAndReference(startDate, reference);
                }
                else if((reference != null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status ==null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("expiration")))
                {
                        return assetRepository.findAllByDateExpirationAndReference(startDate, finalDate, reference);
                }
                else if((reference != null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status ==null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("creation")))
                {
                        return assetRepository.findAllByDateCreationAndReference(startDate, finalDate, reference);
                }
                else throw new RequestException("It is not possible to do a search with the data entered.","400-Bad Request");
        }
        List<LiabilityModel> getAllLiability()
        {
                if((reference != null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status ==null) && (startDate == null)
                        && (finalDate == null))
                {
                        return liabilityRepository.findAllByReference(reference);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate == null)
                        && (finalDate == null))
                {
                        return liabilityRepository.findAllByLiabilityType_LiabilityType(type);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status !=null) && (startDate == null)
                        && (finalDate == null))
                {
                        return liabilityRepository.findAllByLiabilityStatus_LiabilityStatus(status);
                }
                else if((reference == null) && (startValue > 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate == null)
                        && (finalDate == null))
                {
                        return liabilityRepository.findAllByStartValueAndType(startValue, type);
                }
                else if((reference == null) && (startValue > 0) && (finalValue == 0) && (type ==null) && (status !=null) && (startDate == null)
                        && (finalDate == null))
                {
                        return liabilityRepository.findAllByStartValueAndStatus(startValue, status);
                }
                else if((reference == null) && (startValue > 0) && (finalValue > 0) && (type !=null) && (status ==null) && (startDate == null)
                        && (finalDate() == null))
                {
                        return  liabilityRepository.findAllByValueAndType(startValue, finalValue, type);
                }
                else if((reference == null) && (startValue > 0) && (finalValue > 0) && (type ==null) && (status !=null) && (startDate == null)
                        && (finalDate == null))
                {
                        return liabilityRepository.findAllByValueAndStatus(startValue, finalValue, status);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("expiration")))
                {
                        return liabilityRepository.findAllByStartDateExpirationAndType(startDate, type);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("creation")))
                {
                        return liabilityRepository.findAllByStartDateCreationAndType(startDate, type);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status !=null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("expiration")))
                {
                        return  liabilityRepository.findAllByStartDateExpirationAndStatus(startDate, status);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status !=null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("creation")))
                {
                        return  liabilityRepository.findAllByStartDateCreationAndStatus(startDate, status);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("expiration")))
                {
                        return  liabilityRepository.findAllByDateExpirationAndType(startDate, finalDate, type);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status ==null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("creation")))
                {
                        return  liabilityRepository.findAllByDateCreationAndType(startDate, finalDate, type);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status !=null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("expiration")))
                {
                        return liabilityRepository.findAllByDateExpirationAndStatus(startDate, finalDate, status);
                }
                else if((reference == null) && (startValue == 0) && (finalValue == 0) && (type !=null) && (status !=null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("creation")))
                {
                        return liabilityRepository.findAllByDateCreationAndStatus(startDate, finalDate, status);
                }
                else if((reference != null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status ==null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("expiration")))
                {
                        return liabilityRepository.findAllByStartDateExpirationAndReference(startDate, reference);
                }
                else if((reference != null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status ==null) && (startDate != null)
                        && (finalDate == null) && (typeDate.equals("creation")))
                {
                        return liabilityRepository.findAllByStartDateCreationAndReference(startDate, reference);
                }
                else if((reference != null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status ==null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("expiration")))
                {
                        return liabilityRepository.findAllByDateExpirationAndReference(startDate, finalDate, reference);
                }
                else if((reference != null) && (startValue == 0) && (finalValue == 0) && (type ==null) && (status ==null) && (startDate != null)
                        && (finalDate != null) && (typeDate.equals("creation")))
                {
                        return liabilityRepository.findAllByDateCreationAndReference(startDate, finalDate, reference);
                }
                else throw new RequestException("It is not possible to do a search with the data entered.","400-Bad Request");
        }
}
