package com.khaled_amin.book_social_network.identity.capability.exception;

import com.khaled_amin.book_social_network.core.exception.business.BusinessError;
import com.khaled_amin.book_social_network.core.exception.business.BusinessException;

public class CapabilityBusinessException extends BusinessException {

    // ----------------------------------- Constructors ----------------------------------- //

    private CapabilityBusinessException(BusinessError error) {
        super(error);
    }

//    private CapabilityBusinessException(BusinessError error, Throwable cause) {
//        super(error, cause);
//    }
//
//    private CapabilityBusinessException(BusinessError error, String message) {
//        super(error, message);
//    }
//
//    private CapabilityBusinessException(BusinessError error, String message, Throwable cause) {
//        super(error, message, cause);
//    }

    // ----------------------------------- Factories ----------------------------------- //

    public static CapabilityBusinessException notFound() {
        return new CapabilityBusinessException(CapabilityBusinessError.NOT_FOUND);
    }

}