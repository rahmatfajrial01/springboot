package com.backend.aji.controller;

import com.backend.aji.entity.City;
import com.backend.aji.entity.District;
import com.backend.aji.entity.Kelurahan;
import com.backend.aji.entity.Prov;
import com.backend.aji.response.ApiResponse;
import com.backend.aji.service.CsvService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "CSV")
public class CsvController {
    @Autowired
    private CsvService csvService;

    @PostMapping(value = "/upload-csv-prov", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadCsvProv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a non-empty CSV file.");
        }

        try {
            csvService.uploadCsvProv(file);
            return ResponseEntity.ok("CSV uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing CSV: " + e.getMessage());
        }
    }

    @PostMapping(value = "/upload-csv-city", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadCsvCity(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a non-empty CSV file.");
        }

        try {
            csvService.uploadCsvCity(file);
            return ResponseEntity.ok("CSV uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing CSV: " + e.getMessage());
        }
    }

    @Operation(summary = "---Upload kec---", description = "districts === kec")
    @PostMapping(value = "/upload-csv-district", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadCsvDistrict(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a non-empty CSV file.");
        }

        try {
            csvService.uploadCsvDistrict(file);
            return ResponseEntity.ok("CSV uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing CSV: " + e.getMessage());
        }
    }

    @PostMapping(value = "/upload-csv-kelurahan", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadCsvKelurahan(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a non-empty CSV file.");
        }

        try {
            csvService.uploadCsvKelurahan(file);
            return ResponseEntity.ok("CSV uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing CSV: " + e.getMessage());
        }
    }


    @GetMapping("/prov")
    public ResponseEntity<ApiResponse<List<Prov>>> getAllProvs() {
        List<Prov> provs = csvService.getAllProvs();
        return ResponseEntity.ok(new ApiResponse<>("Provs retrieved successfully.", provs));
    }

    @GetMapping("/prov/{id}")
    public ResponseEntity<ApiResponse<Prov>> getProvById(@PathVariable Long id) {
        return csvService.getProvById(id)
                .map(prov -> ResponseEntity.ok(new ApiResponse<>("Prov retrieved successfully.", prov)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Prov not found.", null)));
    }

    @GetMapping("/cities")
    public ResponseEntity<ApiResponse<List<City>>> getAllCities() {
        List<City> cities = csvService.getAllCities();
        return ResponseEntity.ok(new ApiResponse<>("Cities retrieved successfully.", cities));
    }

    @GetMapping("/citiesByProv/{id}")
    public ResponseEntity<ApiResponse<List<City>>> getCitiesByProvId(@PathVariable Long id) {
        List<City> cities = csvService.getCitiesByProvId(id);
        if (cities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("No cities found for the given prov ID.", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("Cities retrieved successfully.", cities));
    }

    @Operation(summary = "---Get All Kec---", description = "districts === kec")
    @GetMapping("/districts")
    public ResponseEntity<ApiResponse<List<District>>> getAllDistricts() {
        List<District> districts = csvService.getAllDistricts();
        return ResponseEntity.ok(new ApiResponse<>("Districts retrieved successfully.", districts));
    }

    @Operation(summary = "---Get Kec by city---", description = "districts === kec")
    @GetMapping("/districtsByCity/{cityId}")
    public ResponseEntity<ApiResponse<List<District>>> getDistrictsByCityId(@PathVariable Long cityId) {
        List<District> districts = csvService.getDistrictsByCityId(cityId);
        return ResponseEntity.ok(new ApiResponse<>("Districts retrieved successfully.", districts));
    }

    @GetMapping("/kelurahans")
    public ResponseEntity<ApiResponse<List<Kelurahan>>> getAllKelurahan() {
        List<Kelurahan> kelurahans = csvService.getAllKelurahan();
        return ResponseEntity.ok(new ApiResponse<>("Kelurahan retrieved successfully.", kelurahans));
    }

    @GetMapping("/kelurahansByDistricts/{districtId}")
    public ResponseEntity<ApiResponse<List<Kelurahan>>> getKelurahanByDistrictId(@PathVariable Long districtId) {
        List<Kelurahan> kelurahans = csvService.getKelurahanByDistrictId(districtId);
        return ResponseEntity.ok(new ApiResponse<>("Kelurahan retrieved successfully.", kelurahans));
    }

}