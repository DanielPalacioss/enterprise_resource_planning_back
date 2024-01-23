package com.salesmanagementplatform.equity.controller;

import com.salesmanagementplatform.equity.error.DataValidation;
import com.salesmanagementplatform.equity.model.AssetModel;
import com.salesmanagementplatform.equity.model.AssetTypeModel;
import com.salesmanagementplatform.equity.service.AssetTypeService;
import com.salesmanagementplatform.equity.service.FilterFields;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/assetType")
@CrossOrigin(origins = "http://localhost:8085")
public class AssetTypeController {

    @Autowired
    private AssetTypeService assetTypeService;

    private final DataValidation dataValidation = new DataValidation();

    @GetMapping
    public ResponseEntity<List<AssetTypeModel>> listAllAssetType(@RequestParam String status)
    {
        return ResponseEntity.ok(assetTypeService.listAllAssetType(status));
    }

    @PostMapping
    public ResponseEntity<?> saveAssetType(@Valid @RequestBody AssetTypeModel assetTypeModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        assetTypeService.saveAssetType(assetTypeModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The asset type has been created successfully.");
    }
    @PutMapping
    public ResponseEntity<?> updateAssetType(@Valid @RequestBody AssetTypeModel assetTypeModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        assetTypeService.updateAssetType(assetTypeModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The asset type has been successfully modified.");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAssetType(@RequestParam Long assetTypeId)
    {
        assetTypeService.deleteAssetType(assetTypeId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The asset type has been successfully deleted.");
    }
}
