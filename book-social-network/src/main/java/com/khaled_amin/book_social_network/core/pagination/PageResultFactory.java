package com.khaled_amin.book_social_network.core.pagination;

import org.springframework.data.domain.Page;

/**
 * Factory responsible for converting Spring Data
 * {@link org.springframework.data.domain.Page}
 * into the application's {@link PageResult}.
 *
 * <p>
 * This class isolates Spring Data pagination types
 * from the rest of the application and ensures a
 * consistent pagination contract across all modules.
 * </p>
 *
 * <h2>Example</h2>
 *
 * <pre>{@code
 * Page<Account> page =
 *         accountJpaRepository.findAll(pageable);
 *
 * PageResult<Account> result =
 *         PageResultFactory.from(page);
 * }</pre>
 *
 * @see PageResult
 */
public final class PageResultFactory {

    private PageResultFactory() {
    }

    public static <T> PageResult<T> from(Page<T> page) {

        return PageResult.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}