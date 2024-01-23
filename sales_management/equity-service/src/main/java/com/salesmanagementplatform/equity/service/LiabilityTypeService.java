package com.salesmanagementplatform.equity.service;

import com.salesmanagementplatform.equity.model.LiabilityTypeModel;

import java.util.List;

public interface LiabilityTypeService {
    List<LiabilityTypeModel> listAllLiabilityType(String status);

    void updateLiabilityType(LiabilityTypeModel updateLiabilityType);

    void deleteLiabilityType(Long liabilityTypeId);

    void saveLiabilityType(LiabilityTypeModel liabilityType);
}
