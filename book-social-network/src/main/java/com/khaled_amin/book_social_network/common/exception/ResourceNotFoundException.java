package com.khaled_amin.book_social_network.common.exception;

import com.khaled_amin.book_social_network.common.utils.EntityNameUtils;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Class<?> entityClass, String field, Object value) {
        super(EntityNameUtils.toReadableName(entityClass)
                + " with " + field
                + " [" + value + "] not found");
    }
}