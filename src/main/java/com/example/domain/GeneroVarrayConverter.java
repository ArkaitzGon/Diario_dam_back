package com.example.domain;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;



@Converter
public class GeneroVarrayConverter implements AttributeConverter<List<String>, String[]> {

    @Override
    public String[] convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.toArray(new String[0]); // Convierte List a String[]
    }

    @Override
    public List<String> convertToEntityAttribute(String[] dbData) {
        if (dbData == null || dbData.length == 0) {
            return null;
        }
        return Arrays.asList(dbData); // Convierte String[] a List<String>
    }
}
