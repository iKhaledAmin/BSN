package com.khaled_amin.book_social_network.core.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


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

@Getter
@Setter
public abstract class PageRequest {
    @Min(value = 0)
    private int page = 0;

    @Min(value = 1)
    @Max(value = 100)
    private int size = 20;

     private SortDirection direction = SortDirection.getDefault();

     public abstract SortField getSortBy();

}
