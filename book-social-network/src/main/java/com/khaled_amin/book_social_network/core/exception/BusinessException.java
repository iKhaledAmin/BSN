package com.khaled_amin.book_social_network.core.exception;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final BaseError error;

    // safe for API
    private final Map<String, Object> details = new LinkedHashMap<>();

    // internalServer only (not exposed)
    private final Map<String, Object> debugDetails = new LinkedHashMap<>();



    // ----------------------------------- Constructors ----------------------------------- //

    protected BusinessException(BaseError error) {
        super(error.getMessage());
        this.error = error;
    }
    protected BusinessException(BaseError error, Throwable cause){
        super(error.getMessage(),cause);
        this.error = error;
    }

    protected BusinessException(BaseError error, String message) {
        super(message);
        this.error = error;
    }
    protected BusinessException(BaseError error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }



    protected BusinessException(BaseError error, String message, Throwable cause, Map<String, Object> details, Map<String,Object> debugDetails) {
        super(message, cause);

        this.error = error;
        this.details.putAll(details);
        this.debugDetails.putAll(debugDetails);
    }




    // ---------------- SAFE DETAILS ---------------- //

    public BusinessException withDetail(String key, Object value) {
        if (value != null) {
            this.details.put(key, value);
        }
        return this;
    }

    // ---------------- DEBUG DETAILS ---------------- //

    public BusinessException withDebug(String key, Object value) {
        if (value != null) {
            this.debugDetails.put(key, value);
        }
        return this;
    }
}