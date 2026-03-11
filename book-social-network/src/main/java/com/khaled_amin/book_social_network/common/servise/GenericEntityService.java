package com.khaled_amin.book_social_network.common.servise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface GenericEntityService {

    <T, ID> Optional<T> getOptionalById(Class<T> entityClass, ID id);

    <T, ID> T getById(Class<T> entityClass, ID id);

    <T, ID> boolean exists(Class<T> entityClass, ID id);

    <T, ID> T getReference(Class<T> entityClass, ID id);

    <T> List<T> findAll(Class<T> entityClass);

    <T> Page<T> findAll(Class<T> entityClass, Pageable pageable);

    <T> List<T> findAll(Class<T> entityClass, Specification<T> spec);
}