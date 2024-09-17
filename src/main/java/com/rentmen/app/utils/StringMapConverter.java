package com.rentmen.app.utils;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class StringMapConverter implements AttributeConverter<Map<Long, Boolean>, String> {
	public StringMapConverter() {
	}

	public String convertToDatabaseColumn(Map<Long, Boolean> attribute) {
		return attribute != null && !attribute.isEmpty() ? UtilFunctions.convertWithStream(attribute) : null;
	}

	public Map<Long, Boolean> convertToEntityAttribute(String dbData) {
		return dbData != null ? UtilFunctions.convertWithStream(dbData) : null;
	}
}
