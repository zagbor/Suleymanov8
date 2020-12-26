package ru.zagbor.practice.suleimanov.task1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    private long id;
    private String name;
    private Set<Specialty> specialties;
    private Account account;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialties=" + specialties +
                ", account=" + account +
                '}';
    }

    public String toStringSpecialties() {
        if(specialties==null){
            return "";
        }
        return specialties.stream()
                .map(specialties -> specialties.getName())
                .collect(Collectors.joining(", "));
    }
}
