package com.salesmanagementplatform.map.service;

import com.salesmanagementplatform.map.model.TownModel;

import java.util.List;

public interface TownService {
    List<TownModel> listAllTown(String department);
}
