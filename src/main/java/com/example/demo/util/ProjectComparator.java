package com.example.demo.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.dto.model.project.ProjectResponse;

@Component
public class ProjectComparator {
    
    private static final Map<String, Comparator<ProjectResponse>> COMPARATORS = new HashMap<>();
    
    static {
        COMPARATORS.put("id", Comparator.comparing(ProjectResponse::getId,
                Comparator.nullsLast(Comparator.naturalOrder())));
        
        COMPARATORS.put("name", Comparator.comparing(ProjectResponse::getName,
                Comparator.nullsLast(Comparator.naturalOrder())));
        
        COMPARATORS.put("startDate", Comparator.comparing(ProjectResponse::getStartDate,
                Comparator.nullsLast(Comparator.naturalOrder())));
        
        COMPARATORS.put("endDate", Comparator.comparing(ProjectResponse::getEndDate,
                Comparator.nullsLast(Comparator.naturalOrder())));
        
        COMPARATORS.put("statusId", Comparator.comparing(ProjectResponse::getStatusId,
                Comparator.nullsLast(Comparator.naturalOrder())));
        
        COMPARATORS.put("categoryId", Comparator.comparing(ProjectResponse::getCategoryId,
                Comparator.nullsLast(Comparator.naturalOrder())));
    }
    
    public Comparator<ProjectResponse> getComparator(String sortBy) {
        return COMPARATORS.getOrDefault(sortBy, COMPARATORS.get("id"));
    }
    
    public Comparator<ProjectResponse> getComparator(String sortBy, boolean ascending) {
        Comparator<ProjectResponse> comparator = getComparator(sortBy);
        return ascending ? comparator : comparator.reversed();
    }
}