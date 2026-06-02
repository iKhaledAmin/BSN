package com.khaled_amin.book_social_network.core.pagination;

/**
 * Represents a sortable field exposed by a module.
 *
 * <p>
 * Implementations define the whitelist of fields
 * that clients are allowed to use for sorting.
 * </p>
 *
 * <p>
 * This prevents exposing internal database column
 * names and avoids arbitrary sorting requests.
 * </p>
 *
 * <h2>Example</h2>
 *
 * <pre>{@code
 * public enum AccountSortField implements SortField {
 *
 *     USERNAME("username"),
 *     CREATED_AT("createdAt"),
 *     LAST_LOGIN("lastLogin");
 *
 *     private final String field;
 *
 *     @Override
 *     public String getField() {
 *         return field;
 *     }
 * }
 * }</pre>
 *
 * <h2>Usage</h2>
 *
 * <pre>{@code
 * GET /accounts?sortBy=USERNAME
 * }</pre>
 */
public interface SortField {
    String getField();

}
