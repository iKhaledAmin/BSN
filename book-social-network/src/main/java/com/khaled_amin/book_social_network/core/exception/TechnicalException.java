package com.khaled_amin.book_social_network.core.exception;

import com.khaled_amin.book_social_network.core.exception.core.BaseException;
import lombok.Getter;


@Getter
public abstract class TechnicalException extends BaseException {

    private final TechnicalError error;


    // ----------------------------------- Constructors ----------------------------------- //

    protected TechnicalException(TechnicalError error) {
        super(error.getMessage());
        this.error = error;
    }
    protected TechnicalException(TechnicalError error, Throwable cause){
        super(error.getMessage(),cause);
        this.error = error;
    }

    protected TechnicalException(TechnicalError error, String message) {
        super(message);
        this.error = error;
    }
    protected TechnicalException(TechnicalError error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

}