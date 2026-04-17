    package com.khaled_amin.book_social_network.core.exception;

    public class CommonException extends BaseException {

        private CommonException(CommonError error, String message) {
            super(error, message);
        }

        // -------------------- Generic -------------------- //

        public static CommonException of(CommonError error) {
            return new CommonException(error, error.getMessage());
        }

        public static CommonException of(CommonError error, String customMessage) {
            return new CommonException(error, customMessage);
        }

        // -------------------- Shortcuts -------------------- //

        public static CommonException validation() {
            return of(CommonError.VALIDATION_ERROR);
        }

        public static CommonException internal() {
            return of(CommonError.INTERNAL_ERROR);
        }

        public static CommonException notFound() {
            return of(CommonError.RESOURCE_NOT_FOUND);
        }

        public static CommonException conflict() {
            return of(CommonError.CONFLICT);
        }

        public static CommonException forbidden() {
            return of(CommonError.FORBIDDEN);
        }
    }