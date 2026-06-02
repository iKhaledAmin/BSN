package com.khaled_amin.book_social_network.core.api;

import com.khaled_amin.book_social_network.core.pagination.PageResult;

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

    public static ApiErrorResponse error(ErrorResponse error) {

        return ApiErrorResponse.builder()
                .meta(buildMeta())
                .error(error)
                .build();
    }


    public static <T> ApiPageResponse<T> page(PageResult<T> pageResult){
        return ApiPageResponse.<T>builder()
                .meta(buildMeta())
                .data(pageResult.getContent())
                .pageInfo(
                        PageInfoResponse.builder()
                                .page(pageResult.getPage())
                                .size(pageResult.getSize())
                                .totalElements(pageResult.getTotalElements())
                                .totalPages(pageResult.getTotalPages())
                                .first(pageResult.isFirst())
                                .last(pageResult.isLast())
                                .hasNext(pageResult.isHasNext())
                                .hasPrevious(pageResult.isHasPrevious())
                                .build()
                )
                .build();
    }



//    public static <T> ApiPageResponse<T> page(
//            List<T> data,
//            int page,
//            int size,
//            long totalElements,
//            int totalPages) {
//
//        return ApiPageResponse.<T>builder()
//                .meta(buildMeta())
//                .data(data)
//                .pageInfo(
//                        PageInfoResponse.builder()
//                                .page(page)
//                                .size(size)
//                                .totalElements(totalElements)
//                                .totalPages(totalPages)
//                                .build()
//                )
//                .build();
//    }

    private static Meta buildMeta() {

        return Meta.builder()
                .timestamp(LocalDateTime.now())
                .requestId(UUID.randomUUID().toString())
                .build();
    }
}