package com.salesmanagementplatform.map.controller;

import com.salesmanagementplatform.map.model.DepartmentModel;
import com.salesmanagementplatform.map.model.TownModel;
import com.salesmanagementplatform.map.service.DepartmentService;
import com.salesmanagementplatform.map.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/map")
@CrossOrigin(origins = "http://localhost:8095")
public class MapController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TownService townService;

    @GetMapping("department")
    ResponseEntity<List<DepartmentModel>> listAllDepartment()
    {
        return ResponseEntity.ok().body(departmentService.listAllDepartment());
    }

    @GetMapping("town")
    ResponseEntity<List<TownModel>> listAllDepartment(@RequestParam String department)
    {
        return ResponseEntity.ok().body(townService.listAllTown(department));
    }
}
