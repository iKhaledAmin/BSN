package com.khaled_amin.book_social_network.core.exception.security;

import com.khaled_amin.book_social_network.core.exception.core.BaseException;
import lombok.Getter;

@Getter
public abstract class SecurityException extends BaseException {
    private final SecurityError error;

    // ----------------------------------- Constructors ----------------------------------- //

    protected SecurityException(SecurityError error) {
        super(error.getMessage());
        this.error = error;
    }
    protected SecurityException(SecurityError error, Throwable cause){
        super(error.getMessage(),cause);
        this.error = error;
    }
    protected SecurityException(SecurityError error, String message) {
        super(message);
        this.error = error;
    }
    protected SecurityException(SecurityError error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

}
