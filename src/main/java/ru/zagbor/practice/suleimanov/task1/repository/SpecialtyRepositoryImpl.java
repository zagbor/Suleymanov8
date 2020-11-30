package ru.zagbor.practice.suleimanov.task1.repository;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    private final String SPECIALTIES = "Specialties.json";


    public SpecialtyRepositoryImpl(){
    }

    @Override
    public Optional<Specialty> getById(Long id) throws IOException {
        Set<Specialty> specialties = getAll();
        return specialties.stream()
                .filter(specialty -> specialty.getId() == id)
                .findFirst();
    }

    @Override
    public Specialty update(Specialty specialty) throws IOException {
        if (specialty.getId() == 0) {
            throw new IllegalStateException();
        }

        deleteById(specialty.getId());
        writeSpecialties(this.getAll());
        return specialty;
    }

    @Override
    public Specialty create(Specialty specialty) throws IOException {
        if (specialty.getId() != 0) {
            throw new IllegalStateException();
        }

        specialty.setId(findMaxId() + 1);
        Set specialties = this.getAll();
        specialties.add(specialty);
        writeSpecialties(specialties);
        return specialty;
    }


    @Override
    public void deleteById(Long id) throws IOException {
        deleteSpecialtyFromAllCustomers(id);
        Set<Specialty> specialties = this.getAll();
        if (!getById(id).isPresent()) {
            return;
        }
        Set<Specialty> updateSpecialties = deleteSpecialtyByIdFromList(id, specialties);
        writeSpecialties(updateSpecialties);
    }


    @Override
    public String specialtiesToStringForBase(Set<Specialty> specialties) {
        if (specialties == null) {
            return "";
        }
        return specialties.stream()
                .map(s -> String.valueOf(s.getId()))
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException {
        Set<Specialty> allSpecialties = getAll();
        return allSpecialties.stream()
                .filter(specialty -> !specialtiesCustomer.contains(specialty))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isSpecialtyExist(long id) throws IOException {
        return getAll().stream()
                .anyMatch(specialty -> specialty.getId() == id);
    }


    @Override
    public Set<Specialty> getAll() throws IOException {
        Type itemsListType = new TypeToken<Set<Specialty>>() {
        }.getType();
        String data = new String(Files.readAllBytes(Paths.get(SPECIALTIES)), StandardCharsets.UTF_8);
        if (data.isEmpty()) {
            return new HashSet<Specialty>();
        }
        return new Gson().fromJson(data, itemsListType);
    }


    private Long findMaxId() throws IOException {
        return this.getAll().stream()
                .map(specialty -> specialty.getId())
                .max(Comparator.naturalOrder()).orElse(0L);
    }

    private void deleteSpecialtyFromAllCustomers(Long specialtyId) throws IOException {
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        new CustomerRepositoryImpl().getAll().forEach(customer -> {
            customer.setSpecialties(
                    customer.getSpecialties()
                            .stream()
                            .filter(specialty -> specialty.getId() != specialtyId)
                            .collect(Collectors.toSet()));
            try {
                customerRepository.update(customer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void writeSpecialties(Set<Specialty> specialties) throws IOException {
        Files.newBufferedWriter(Paths.get(SPECIALTIES), StandardOpenOption.TRUNCATE_EXISTING);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SPECIALTIES, true))) {
            String sSpecialties = new Gson().toJson(specialties);
            writer.write(sSpecialties);
            writer.flush();
        }
    }

    private Set<Specialty> deleteSpecialtyByIdFromList(long id, Set<Specialty> specialties) {
        return specialties.stream()
                .filter(specialty -> specialty.getId() != id)
                .collect(Collectors.toSet());
    }
}
