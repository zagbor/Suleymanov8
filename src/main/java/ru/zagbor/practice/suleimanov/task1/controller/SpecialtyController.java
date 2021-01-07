package ru.zagbor.practice.suleimanov.task1.controller;


import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.service.SpecialtyService;
import ru.zagbor.practice.suleimanov.task1.service.impl.SpecialtyServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController() {
        specialtyService = new SpecialtyServiceImpl();
    }


    public Optional<Specialty> getSpecialtyForId(long id)  {
        return specialtyService.getById(id);
    }


    public Set<Specialty> specialtiesWhichCanAdd(Set<Specialty> specialties) {
        return specialtyService.findWhichCanAdd(specialties);
    }


    public boolean isSpecialtyExist(long id) throws IOException, SQLException {
        return specialtyService.isSpecialtyExist(id);
    }


    public Specialty create(Specialty specialty){
        return specialtyService.create(specialty);
    }


    public Set<Specialty> getAll(){
        return specialtyService.getAll();
    }


    public void deleteById(Long id) {
        specialtyService.deleteById(id);
    }

    public void addSpecialtyCustomer(Customer customer, Long specialtyId) {
        specialtyService.addSpecialtyCustomer(customer, specialtyId);
    }

    public void deleteSpecialtyCustomer(Long specialtyId, Long customerId) {
        specialtyService.deleteSpecialtyCustomer(specialtyId, customerId);
    }

}