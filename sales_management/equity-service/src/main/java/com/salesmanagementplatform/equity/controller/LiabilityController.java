package com.salesmanagementplatform.equity.controller;

import com.salesmanagementplatform.equity.error.DataValidation;
import com.salesmanagementplatform.equity.model.AssetModel;
import com.salesmanagementplatform.equity.model.LiabilityModel;
import com.salesmanagementplatform.equity.service.FilterFields;
import com.salesmanagementplatform.equity.service.LiabilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/liability")
@CrossOrigin(origins = "http://localhost:8085")
public class LiabilityController {

    @Autowired
    private LiabilityService liabilityService;

    private final DataValidation dataValidation = new DataValidation();

    @GetMapping()
    public ResponseEntity<List<LiabilityModel>> listAllLiability()
    {
        return ResponseEntity.ok(liabilityService.listAllLiability());
    }

    @GetMapping("filterAssetSearch")
    public ResponseEntity<List<LiabilityModel>> filterLiabilitySearch(@Valid @RequestBody FilterFields filterFields, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        return ResponseEntity.ok(liabilityService.filterLiabilitySearch(filterFields));
    }

    @PostMapping
    public ResponseEntity<?> saveLiability(@Valid @RequestBody LiabilityModel liabilityModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        liabilityService.saveLiability(liabilityModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The liability has been created successfully.");
    }
    @PutMapping
    public ResponseEntity<?> updateLiability(@Valid @RequestBody LiabilityModel liabilityModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        liabilityService.updateLiability(liabilityModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The liability has been successfully modified.");
    }

}
