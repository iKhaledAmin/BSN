package com.khaled_amin.book_social_network.core.logging.audit;

import com.khaled_amin.book_social_network.core.exception.business.BusinessException;
import com.khaled_amin.book_social_network.core.exception.policy.PolicyException;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.core.exception.validation.ValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ExceptionLogger {

    void log(BusinessException ex);

    void log(PolicyException ex);

    void log(TechnicalException ex);

    void log(ValidationException ex);

    void log(MethodArgumentNotValidException exception);

    void log(Exception ex);
}