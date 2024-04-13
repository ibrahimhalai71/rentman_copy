package com.rentmen.app.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtilFunctions {
	public static <T> T mergeObjects(T target, T source) throws IllegalAccessException {
        List<Field> baseClazz = Arrays.asList(target.getClass().getSuperclass().getDeclaredFields());
        List<Field> clazz = Arrays.asList(target.getClass().getDeclaredFields());
        List<Field> allFields = Stream.concat(baseClazz.stream(), clazz.stream())
            .collect(Collectors.toList());
        for (Field field : allFields) {
            field.setAccessible(true);
            Object value = field.get(source);
            if (value != null && !Modifier.isFinal(field.getModifiers())) {
                field.set(target, value);
            }
        }
        return target;
    }
}
