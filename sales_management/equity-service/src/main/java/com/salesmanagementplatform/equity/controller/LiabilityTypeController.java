package com.salesmanagementplatform.equity.controller;

import com.salesmanagementplatform.equity.error.DataValidation;
import com.salesmanagementplatform.equity.model.AssetTypeModel;
import com.salesmanagementplatform.equity.model.LiabilityTypeModel;
import com.salesmanagementplatform.equity.service.LiabilityTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/liabilityType")
@CrossOrigin(origins = "http://localhost:8085")
public class LiabilityTypeController {

    @Autowired
    private LiabilityTypeService liabilityTypeService;

    private final DataValidation dataValidation = new DataValidation();

    @GetMapping
    public ResponseEntity<List<LiabilityTypeModel>> listAllLiabilityType(@RequestParam String status)
    {
        return ResponseEntity.ok(liabilityTypeService.listAllLiabilityType(status));
    }

    @PostMapping
    public ResponseEntity<?> saveLiabilityType(@Valid @RequestBody LiabilityTypeModel liabilityTypeModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        liabilityTypeService.saveLiabilityType(liabilityTypeModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The liability type has been created successfully.");
    }
    @PutMapping
    public ResponseEntity<?> updateLiabilityType(@Valid @RequestBody LiabilityTypeModel liabilityTypeModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        liabilityTypeService.updateLiabilityType(liabilityTypeModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The liability type has been successfully modified.");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteLiabilityType(@RequestParam Long liabilityTypeId)
    {
        liabilityTypeService.deleteLiabilityType(liabilityTypeId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The liability type has been successfully deleted.");
    }
}
