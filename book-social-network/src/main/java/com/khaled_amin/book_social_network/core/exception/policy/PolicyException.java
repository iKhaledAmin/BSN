package com.khaled_amin.book_social_network.core.exception.policy;

import com.khaled_amin.book_social_network.core.exception.core.BaseException;
import lombok.Getter;

@Getter
public abstract class PolicyException extends BaseException {
    private final PolicyError error;


    // ----------------------------------- Constructors ----------------------------------- //

    protected PolicyException(PolicyError error) {
        super(error.getMessage());
        this.error = error;
    }
    protected PolicyException(PolicyError error, Throwable cause){
        super(error.getMessage(),cause);
        this.error = error;
    }

    protected PolicyException(PolicyError error, String message) {
        super(message);
        this.error = error;
    }
    protected PolicyException(PolicyError error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }



}
