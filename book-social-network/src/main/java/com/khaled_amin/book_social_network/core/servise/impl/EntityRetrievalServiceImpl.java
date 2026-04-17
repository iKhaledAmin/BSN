package com.khaled_amin.book_social_network.core.servise.impl;

import com.khaled_amin.book_social_network.core.persistence.BaseRepository;
import com.khaled_amin.book_social_network.core.servise.EntityRetrievalService;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class EntityRetrievalServiceImpl implements EntityRetrievalService {

    private final Repositories repositories;

    public EntityRetrievalServiceImpl(ApplicationContext context) {
        this.repositories = new Repositories(context);
    }

    @SuppressWarnings("unchecked")
    private <T, ID> BaseRepository<T, ID> getRepository(Class<T> entityClass) {

        return (BaseRepository<T, ID>) repositories
                .getRepositoryFor(entityClass)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No persistence found for " + entityClass.getSimpleName()));
    }

    @Override
    public <T, ID> Optional<T> getOptionalById(Class<T> entityClass, ID id) {

        return getRepository(entityClass).findById(id);
    }

    @Override
    public <T, ID, E extends RuntimeException> T getById(Class<T> entityClass, ID id, Supplier<E> notFoundExceptionSupplier) {
        return getRepository(entityClass)
                .findById(id)
                .orElseThrow(notFoundExceptionSupplier);
    }

    @Override
    public <T, ID> boolean exists(Class<T> entityClass, ID id) {
        return getRepository(entityClass).existsById(id);
    }

    @Override
    public <T, ID> T getReference(Class<T> entityClass, ID id) {
        return getRepository(entityClass).getReferenceById(id);
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {

        return getRepository(entityClass).findAll();
    }

    @Override
    public <T> Page<T> findAll(Class<T> entityClass, Pageable pageable) {

        return getRepository(entityClass).findAll(pageable);
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass, Specification<T> spec) {

        return getRepository(entityClass).findAll(spec);
    }
}