package com.rentmen.app.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;

public class StringMapConverter implements AttributeConverter<Map<Long, Boolean>, String> {

	@Override
	public String convertToDatabaseColumn(Map<Long, Boolean> attribute) {
		return attribute == null || attribute.isEmpty() ? null : UtilFunctions.convertWithStream(attribute);
	}

	@Override
	public Map<Long, Boolean> convertToEntityAttribute(String dbData) {
		return dbData != null ? UtilFunctions.convertWithStream(dbData) : null;
	}
}
