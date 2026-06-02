package com.khaled_amin.book_social_network.core.pagination;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Generic pagination response model.
 *
 * <p>
 * Encapsulates paginated content together with all
 * metadata required by API consumers to navigate
 * through pages.
 * </p>
 *
 * <h2>Example JSON Response</h2>
 *
 * <pre>{@code
 * {
 *   "content": [...],
 *   "page": 0,
 *   "size": 20,
 *   "totalElements": 125,
 *   "totalPages": 7,
 *   "first": true,
 *   "last": false,
 *   "hasNext": true,
 *   "hasPrevious": false
 * }
 * }</pre>
 *
 * @param <T> content type contained in the page
 *
 * @see PageRequest
 * @see PageResultFactory
 */

@Getter
@Builder
public class PageResult<T> {

    private List<T> content;

    private int page;

    private int size;

    private long totalElements;

    private long totalPages;

    private boolean first;

    private boolean last;

    private boolean hasNext;

    private boolean hasPrevious;
}