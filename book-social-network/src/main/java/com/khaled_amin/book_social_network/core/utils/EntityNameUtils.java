package com.khaled_amin.book_social_network.core.utils;

public final class EntityNameUtils {

    private EntityNameUtils() {}

    public static String toReadableName(Class<?> entityClass) {

        String name = entityClass.getSimpleName();

        // Insert space before uppercase letters
        String readable = name.replaceAll("([a-z])([A-Z])", "$1 $2");

        // Make first letter uppercase only
        return readable.substring(0, 1).toUpperCase() + readable.substring(1).toLowerCase();
    }
}