package com.khaled_amin.book_social_network.core.exception;

import lombok.Getter;


import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public abstract class BaseException extends RuntimeException {

    private final BaseError error;

    // safe for API
    private final Map<String, Object> details = new LinkedHashMap<>();

    // internal only (not exposed)
    private final Map<String, Object> debugDetails = new LinkedHashMap<>();



    protected BaseException(BaseError error, String message) {
        super(message != null ? message : error.getMessage());
        this.error = error;
    }

    // ---------------- SAFE DETAILS ---------------- //

    public BaseException withDetail(String key, Object value) {
        if (value != null) {
            this.details.put(key, value);
        }
        return this;
    }

    // ---------------- DEBUG DETAILS ---------------- //

    public BaseException withDebug(String key, Object value) {
        if (value != null) {
            this.debugDetails.put(key, value);
        }
        return this;
    }
}