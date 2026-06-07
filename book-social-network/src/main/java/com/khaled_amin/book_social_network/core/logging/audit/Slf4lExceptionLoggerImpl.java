package com.khaled_amin.book_social_network.core.logging.audit;

import com.khaled_amin.book_social_network.core.exception.business.BusinessException;
import com.khaled_amin.book_social_network.core.exception.core.ErrorType;
import com.khaled_amin.book_social_network.core.exception.policy.PolicyException;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.core.exception.validation.ValidationException;
import com.khaled_amin.book_social_network.core.logging.core.LogCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j(topic = "ExceptionLogger")
@Component
public class Slf4lExceptionLoggerImpl implements ExceptionLogger {

    @Override
    public void log(BusinessException ex) {

        log.atWarn()
                .addKeyValue("category", LogCategory.EXCEPTION)
                .addKeyValue("type", ex.getError().getType())
                .addKeyValue("domain", ex.getError().getDomain())
                .addKeyValue("errorCode", ex.getError().getCode())
                .addKeyValue("errorMessage", ex.getMessage())
                .addKeyValue("errorDetails", ex.getDebugDetails())
                .log("business exception");
    }

    @Override
    public void log(PolicyException ex) {

        log.atWarn()
                .addKeyValue("category", LogCategory.EXCEPTION)
                .addKeyValue("type", ex.getError().getType())
                .addKeyValue("domain", ex.getError().getDomain())
                .addKeyValue("errorCode", ex.getError().getCode())
                .addKeyValue("errorMessage", ex.getMessage())
                .addKeyValue("errorDetails", ex.getDebugDetails())
                .log("policy exception");
    }

    @Override
    public void log(ValidationException ex) {

        log.atDebug()
                .addKeyValue("category", LogCategory.EXCEPTION)
                .addKeyValue("type", ex.getError().getType())
                .addKeyValue("domain", ex.getError().getDomain())
                .addKeyValue("errorCode", ex.getError().getCode())
                .addKeyValue("errorMessage", ex.getMessage())
                .addKeyValue("errorDetails", ex.getDebugDetails())
                .log("validation exception");
    }

    @Override
    public void log(MethodArgumentNotValidException ex) {

        Map<String, String> details = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        details.put(error.getField(), error.getDefaultMessage())
                );

        log.atDebug()
                .addKeyValue("category", LogCategory.EXCEPTION)
                .addKeyValue("type", ErrorType.VALIDATION)
                .addKeyValue("domain", "METHOD_ARGUMENT")
                .addKeyValue("errorCode", "METHOD_ARGUMENT_NOT_VALID")
                .addKeyValue("errorMessage", "Validation failed")
                .addKeyValue("errorDetails", details)
                .log("method argument validation failed");
    }

    @Override
    public void log(TechnicalException ex) {

        log.atError()
                .addKeyValue("category", LogCategory.EXCEPTION)
                .addKeyValue("type", ex.getError().getType())
                .addKeyValue("domain", ex.getError().getDomain())
                .addKeyValue("errorCode", ex.getError().getCode())
                .addKeyValue("errorMessage", ex.getMessage())
                .addKeyValue("errorDetails", ex.getDebugDetails())
                .setCause(ex)
                .log("technical exception");
    }

    @Override
    public void log(Exception ex) {

        log.atError()
                .addKeyValue("category", LogCategory.EXCEPTION)
                .addKeyValue("type", ErrorType.TECHNICAL)
                .addKeyValue("domain", "SERVER")
                .addKeyValue("errorCode", "INTERNAL_SERVER_ERROR")
                .addKeyValue("errorMessage", ex.getMessage())
                .setCause(ex)
                .log("unexpected exception");
    }
}