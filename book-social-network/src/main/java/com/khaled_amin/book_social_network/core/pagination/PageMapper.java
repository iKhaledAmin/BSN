package com.khaled_amin.book_social_network.core.pagination;

import java.util.List;
import java.util.function.Function;

/**
 * Utility responsible for transforming paginated content from one type
 * into another while preserving all pagination metadata.
 *
 * <p>
 * Commonly used when converting domain entities into API response DTOs.
 * </p>
 *
 * <h2>Example</h2>
 *
 * <pre>{@code
 * PageResult<Account> accounts = accountService.getAll(request);
 *
 * PageResult<AccountResponse> response =
 *         PageMapper.map(accounts, accountMapper::toResponse);
 * }</pre>
 *
 * <p>
 * All pagination information such as page number, size,
 * total elements, total pages, and navigation flags
 * are copied automatically.
 * </p>
 *
 * @see PageResult
 */
public final class PageMapper {

    private PageMapper() {
    }

    public static <T, R> PageResult<R> map(PageResult<T> source, Function<T, R> mapper) {

        List<R> content = source.getContent()
                .stream()
                .map(mapper)
                .toList();

        return PageResult.<R>builder()
                .content(content)
                .page(source.getPage())
                .size(source.getSize())
                .totalElements(source.getTotalElements())
                .totalPages(source.getTotalPages())
                .first(source.isFirst())
                .last(source.isLast())
                .hasNext(source.isHasNext())
                .hasPrevious(source.isHasPrevious())
                .build();
    }
}