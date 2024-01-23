package com.salesmanagementplatform.equity.controller;

import com.salesmanagementplatform.equity.error.DataValidation;
import com.salesmanagementplatform.equity.model.AssetModel;
import com.salesmanagementplatform.equity.service.AssetService;
import com.salesmanagementplatform.equity.service.FilterFields;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/asset")
@CrossOrigin(origins = "http://localhost:8085")
public class AssetController {

    @Autowired
    private AssetService assetService;
    private final DataValidation dataValidation = new DataValidation();

    @GetMapping()
    public ResponseEntity<List<AssetModel>> listAllAsset()
    {
        return ResponseEntity.ok(assetService.listAllAsset());
    }

    @GetMapping("filterAssetSearch")
    public ResponseEntity<List<AssetModel>> filterAssetSearch(@Valid @RequestBody FilterFields filterFields, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        return ResponseEntity.ok(assetService.filterAssetSearch(filterFields));
    }

    @PostMapping
    public ResponseEntity<?> saveAsset(@Valid @RequestBody AssetModel assetModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        assetService.saveAsset(assetModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The asset has been created successfully.");
    }
    @PutMapping
    public ResponseEntity<?> updateAsset(@Valid @RequestBody AssetModel assetModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        assetService.updateAsset(assetModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The asset has been successfully modified.");
    }
}
