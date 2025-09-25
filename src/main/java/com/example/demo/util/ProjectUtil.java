package com.example.demo.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.model.project.ProjectResponse;

@Component
public class ProjectUtil {
    
    @Autowired
    private ProjectComparator projectComparator;

    public String generateProjectCode() {
        String uuid = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 6)
                .toUpperCase();
        return "PRJ-" + uuid;
    }

    public List<ProjectResponse> paginateAndSort(
            List<ProjectResponse> projects,
            int page,
            int size,
            String sortBy,
            boolean ascending) {
        
        if (projects == null || projects.isEmpty()) {
            return new ArrayList<>();
        }
        
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }
        
        Comparator<ProjectResponse> comparator = projectComparator.getComparator(sortBy, ascending);
        
        return projects.stream()
                .sorted(comparator)
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }
}