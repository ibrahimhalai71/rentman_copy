
package com.rentmen.app.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ";";

    public StringListConverter() {
    }

    public String convertToDatabaseColumn(List<String> stringList) {
        return stringList != null ? String.join(";", stringList) : "";
    }

    public List<String> convertToEntityAttribute(String string) {
        return (List)(string != null ? Arrays.asList(string.split(";")) : new ArrayList());
    }
}
