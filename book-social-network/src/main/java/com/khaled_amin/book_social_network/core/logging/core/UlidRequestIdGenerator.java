package com.khaled_amin.book_social_network.core.logging.core;

import com.github.f4b6a3.ulid.UlidCreator;
import org.springframework.stereotype.Component;

@Component
public class UlidRequestIdGenerator implements RequestIdGenerator {

    @Override
    public String generate() {

        return "REQ_" + UlidCreator.getUlid();
    }
}