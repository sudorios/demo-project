package com.example.demo.repository.util;

import org.springframework.stereotype.Component;

@Component
public class ProjectUtil {

    public String generateProjectCode() {
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        return "PRJ-" + uuid;
    }

}
