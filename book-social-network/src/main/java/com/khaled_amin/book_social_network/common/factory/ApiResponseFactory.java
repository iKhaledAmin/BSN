package com.khaled_amin.book_social_network.common.factory;

import com.khaled_amin.book_social_network.common.dto.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class ApiResponseFactory {

    private ApiResponseFactory() {
    }

    public static <T> ApiResponse<T> success(T data) {

        return ApiResponse.<T>builder()
                .meta(buildMeta())
                .data(data)
                .build();
    }

    public static <T> PageResponse<T> page(
            List<T> data,
            int page,
            int size,
            long totalElements,
            int totalPages) {

        return PageResponse.<T>builder()
                .meta(buildMeta())
                .data(data)
                .page(
                        PageInfo.builder()
                                .page(page)
                                .size(size)
                                .totalElements(totalElements)
                                .totalPages(totalPages)
                                .build()
                )
                .build();
    }

    public static ErrorResponse error(ApiError error) {

        return ErrorResponse.builder()
                .meta(buildMeta())
                .error(error)
                .build();
    }

    private static Meta buildMeta() {

        return Meta.builder()
                .timestamp(LocalDateTime.now())
                .requestId(UUID.randomUUID().toString())
                .version("v1")
                .build();
    }
}