package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    
    public String extractFirstWord(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.split(" ")[0];
    }


}
