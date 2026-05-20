package com.khaled_amin.book_social_network.core.exception.core;


import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public abstract class BaseException extends RuntimeException {

    // safe for API
    protected final Map<String, Object> details = new LinkedHashMap<>();

    // internalServer only (not exposed)
    protected final Map<String, Object> debugDetails = new LinkedHashMap<>();



    // ----------------------------------- Constructors ----------------------------------- //

    protected BaseException(String message) {
        super(message);
    }
    protected BaseException(String message, Throwable cause) {
        super(message, cause);
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
