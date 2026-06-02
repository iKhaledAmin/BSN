package com.khaled_amin.book_social_network.core.pagination;

import org.springframework.data.domain.Sort;

/**
 * Factory responsible for converting application-level pagination requests
 * into Spring Data {@link org.springframework.data.domain.Pageable} instances.
 *
 * <p>
 * This class acts as an anti-corruption layer between the application's
 * pagination model and the Spring Data pagination model.
 * Domain and application layers should work exclusively with
 * {@link PageRequest} and should never depend directly on Spring Data types.
 * </p>
 *
 * <h2>Example</h2>
 *
 * <pre>{@code
 * AccountPageRequest request = new AccountPageRequest();
 * request.setPage(0);
 * request.setSize(20);
 * request.setDirection(SortDirection.DESC);
 * request.setSortBy(AccountSortField.CREATED_AT);
 *
 * Pageable pageable = PageableFactory.from(request);
 * }</pre>
 *
 * @see PageRequest
 * @see PageResult
 */
public final class PageableFactory {

    private PageableFactory() {
    }

    public static org.springframework.data.domain.PageRequest from(PageRequest request) {

        Sort sort = Sort.by(
                Sort.Direction.valueOf(request.getDirection().name()),
                request.getSortBy().getField()
        );

        return org.springframework.data.domain.PageRequest.of(
                request.getPage(),
                request.getSize(),
                sort
        );
    }
}