package com.khaled_amin.book_social_network.core.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SystemException extends RuntimeException {

    private final String code;

    private final String message;

    // safe for API
    private final Map<String, Object> details = new LinkedHashMap<>();

    // internalServer only (not exposed)
    private final Map<String, Object> debugDetails = new LinkedHashMap<>();




    protected SystemException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public SystemException( String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    // ---------------- SAFE DETAILS ---------------- //

    public SystemException withDetail(String key, Object value) {
        if (value != null) {
            this.details.put(key, value);
        }
        return this;
    }

    // ---------------- DEBUG DETAILS ---------------- //

    public SystemException withDebug(String key, Object value) {
        if (value != null) {
            this.debugDetails.put(key, value);
        }
        return this;
    }
}