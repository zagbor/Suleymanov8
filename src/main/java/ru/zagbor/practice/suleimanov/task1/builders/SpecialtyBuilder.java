package ru.zagbor.practice.suleimanov.task1.builders;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpecialtyBuilder {

    public Set<Specialty> builderListSpecialty(CachedRowSet cachedRowSet) throws SQLException {
        Set<Specialty> specialties = new HashSet<>();
        while (cachedRowSet.next()) {
            specialties.add(builder(cachedRowSet));
        }
        return specialties;
    }

    private Specialty builder(CachedRowSet cachedRowSet) throws SQLException {
        Specialty specialty = new Specialty();
        specialty.setId(cachedRowSet.getLong("id"));
        specialty.setName(cachedRowSet.getString("name"));
        return specialty;
    }


    public Map<Long, List<Specialty>> builderListSpecialtyCustomers(CachedRowSet cachedRowSet) throws SQLException {
        Map<Long, List<Specialty>> longSpecialtyMap = new HashMap<>();
        while (cachedRowSet.next()) {
            Specialty specialty = builder(cachedRowSet);
            Long key = idCustomerParser(cachedRowSet);
            if (!longSpecialtyMap.containsKey(key)) {
                List<Specialty> specialties = new LinkedList<>();
                specialties.add(specialty);
                longSpecialtyMap.put(key, specialties);
            } else {
                longSpecialtyMap.get(key)
                        .add(specialty);
            }
        }
        // stream,groupBy
        // map.computeIfAbsent или map.putIfAbsent
        return longSpecialtyMap;
    }

    public Long idCustomerParser(CachedRowSet cachedRowSet) throws SQLException {
        return cachedRowSet.getLong("id");
    }
}
