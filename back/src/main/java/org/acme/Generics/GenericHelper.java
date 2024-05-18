package org.acme.Generics;

import java.lang.reflect.Field;

public class GenericHelper {

    public static void updateEntity(Object target, Object source) {
        Field[] fields = source.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to update entity", e);
            }
        }
    }
}
