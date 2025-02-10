package com.backend.aji.service;

import com.backend.aji.entity.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CsvService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void uploadCsvProv(MultipartFile file) {
        System.out.println("Starting Prov CSV upload...");

        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {

                if (nextLine.length < 2) {
                    System.out.println("Not enough columns, skipping line: " + String.join(";", nextLine));
                    continue;
                }

                Long id = Long.valueOf(nextLine[0].trim());
                String name = nextLine[1].trim();

                System.out.println("Processing ID: " + id + ", Name: " + name);

                Prov existingProv = entityManager.find(Prov.class, id);
                if (existingProv != null) {

                    existingProv.setName(name);
                    entityManager.merge(existingProv);
                    System.out.println("Updated existing Prov with ID: " + id);
                } else {
                    Prov prov = new Prov();
                    prov.setId(id);
                    prov.setName(name);
                    entityManager.persist(prov);
                    System.out.println("Created new Prov with ID: " + id);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException("Error reading CSV: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file: " + e.getMessage(), e);
        }
    }

        @Transactional
        public void uploadCsvCity(MultipartFile file) {
            System.out.println("Starting City CSV upload...");

            try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                    .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                    .build()) {

                String[] nextLine;

                while ((nextLine = csvReader.readNext()) != null) {

                    if (nextLine.length < 3) {
                        System.out.println("Not enough columns, skipping line: " + String.join(";", nextLine));
                        continue;
                    }

                    Long cityId = Long.valueOf(nextLine[0].trim());
                    String cityName = nextLine[1].trim();
                    Long provId = Long.valueOf(nextLine[2].trim());

                    System.out.println("Processing City ID: " + cityId + ", Name: " + cityName + ", Prov ID: " + provId);

                    Prov prov = entityManager.find(Prov.class, provId);
                    if (prov == null) {
                        System.out.println("Province with ID " + provId + " not found, skipping...");
                        continue;
                    }

                    City existingCity = entityManager.find(City.class, cityId);
                    if (existingCity != null) {

                        existingCity.setName(cityName);
                        existingCity.setProv(prov);
                        entityManager.merge(existingCity);
                        System.out.println("Updated existing City with ID: " + cityId);
                    } else {
                        // Create new city entity
                        City city = new City();
                        city.setId(cityId);
                        city.setName(cityName);
                        city.setProv(prov);
                        entityManager.persist(city);
                        System.out.println("Created new City with ID: " + cityId);
                    }
                }
            } catch (CsvValidationException e) {
                throw new RuntimeException("Error reading CSV: " + e.getMessage(), e);
            } catch (IOException e) {
                throw new RuntimeException("Error processing file: " + e.getMessage(), e);
            }
        }

    @Transactional
    public void uploadCsvDistrict(MultipartFile file) {
        System.out.println("Starting District CSV upload...");

        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {

                if (nextLine.length < 3) {
                    System.out.println("Not enough columns, skipping line: " + String.join(";", nextLine));
                    continue;
                }

                Long districtId = Long.valueOf(nextLine[0].trim());
                String districtName = nextLine[1].trim();
                Long cityId = Long.valueOf(nextLine[2].trim());

                System.out.println("Processing District ID: " + districtId + ", Name: " + districtName + ", City ID: " + cityId);

                City city = entityManager.find(City.class, cityId);
                if (city == null) {
                    System.out.println("City with ID: " + cityId + " does not exist, skipping district creation.");
                    continue;
                }

                District district = entityManager.find(District.class, districtId);
                if (district == null) {
                    district = new District();
                    district.setId(districtId);
                    district.setName(districtName);
                    district.setCity(city);
                    entityManager.persist(district);
                    System.out.println("Created new District with ID: " + districtId);
                } else {
                    district.setName(districtName);
                    district.setCity(city);
                    entityManager.merge(district);
                    System.out.println("Updated existing District with ID: " + districtId);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException("Error reading CSV: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void uploadCsvKelurahan(MultipartFile file) {
        System.out.println("Starting Kelurahan CSV upload...");

        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {

                if (nextLine.length < 3) {
                    System.out.println("Not enough columns, skipping line: " + String.join(";", nextLine));
                    continue;
                }

                Long kelurahanId = Long.valueOf(nextLine[0].trim());
                String kelurahanName = nextLine[1].trim();
                Long districtId = Long.valueOf(nextLine[2].trim());

                System.out.println("Processing Kelurahan ID: " + kelurahanId + ", Name: " + kelurahanName + ", District ID: " + districtId);

                District district = entityManager.find(District.class, districtId);
                if (district == null) {
                    System.out.println("District with ID: " + districtId + " does not exist, skipping kelurahan creation.");
                    continue;
                }

                Kelurahan kelurahan = entityManager.find(Kelurahan.class, kelurahanId);
                if (kelurahan == null) {
                    kelurahan = new Kelurahan();
                    kelurahan.setId(kelurahanId);
                    kelurahan.setName(kelurahanName);
                    kelurahan.setDistrict(district);
                    entityManager.persist(kelurahan);
                    System.out.println("Created new Kelurahan with ID: " + kelurahanId);
                } else {
                    kelurahan.setName(kelurahanName);
                    kelurahan.setDistrict(district);
                    entityManager.merge(kelurahan);
                    System.out.println("Updated existing Kelurahan with ID: " + kelurahanId);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException("Error reading CSV: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file: " + e.getMessage(), e);
        }
    }

    public List<Prov> getAllProvs() {
        return entityManager.createQuery("SELECT p FROM Prov p", Prov.class).getResultList();
    }

    public Optional<Prov> getProvById(Long id) {
        Prov prov = entityManager.find(Prov.class, id);
        return Optional.ofNullable(prov);
    }

    public List<City> getAllCities() {
        return entityManager.createQuery("SELECT c FROM City c", City.class)
                .getResultList();
    }

    public List<City> getCitiesByProvId(Long provId) {
        return entityManager.createQuery("SELECT c FROM City c WHERE c.prov.id = :provId", City.class)
                .setParameter("provId", provId)
                .getResultList();
    }

    public List<District> getAllDistricts() {
        return entityManager.createQuery("SELECT d FROM District d", District.class)
                .getResultList();
    }

    public List<District> getDistrictsByCityId(Long cityId) {
        return entityManager.createQuery("SELECT d FROM District d WHERE d.city.id = :cityId", District.class)
                .setParameter("cityId", cityId)
                .getResultList();
    }

    public List<Kelurahan> getAllKelurahan() {
        return entityManager.createQuery("SELECT k FROM Kelurahan k", Kelurahan.class).getResultList();
    }

    public List<Kelurahan> getKelurahanByDistrictId(Long districtId) {
        return entityManager.createQuery("SELECT k FROM Kelurahan k WHERE k.district.id = :districtId", Kelurahan.class)
                .setParameter("districtId", districtId)
                .getResultList();
    }

}

