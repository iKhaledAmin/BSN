package com.khaled_amin.book_social_network.core.exception.validation;

import com.khaled_amin.book_social_network.core.exception.core.BaseException;
import lombok.Getter;

@Getter
public abstract class ValidationException extends BaseException {
    private final ValidationError error;


    // ----------------------------------- Constructors ----------------------------------- //

    protected ValidationException(ValidationError error) {
        super(error.getMessage());
        this.error = error;
    }
    protected ValidationException(ValidationError error, Throwable cause){
        super(error.getMessage(),cause);
        this.error = error;
    }

    protected ValidationException(ValidationError error, String message) {
        super(message);
        this.error = error;
    }
    protected ValidationException(ValidationError error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

}


