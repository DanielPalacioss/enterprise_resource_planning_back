package com.salesmanagementplatform.equity.service;

import com.salesmanagementplatform.equity.model.LiabilityModel;

import java.util.List;

public interface LiabilityService {
    List<LiabilityModel> filterLiabilitySearch(FilterFields filterFields);

    List<LiabilityModel> listAllLiability();

    void updateLiability(LiabilityModel updateLiability);

    void saveLiability(LiabilityModel liability);
}
