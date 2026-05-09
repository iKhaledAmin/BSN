    package com.khaled_amin.book_social_network.core.exception;

    public class GlobalException extends BusinessException {

        private GlobalException(GlobalError error, String message) {
            super(error, message);
        }

        // -------------------- Generic -------------------- //

        public static GlobalException of(GlobalError error) {
            return new GlobalException(error, error.getMessage());
        }

        public static GlobalException of(GlobalError error, String customMessage) {
            return new GlobalException(error, customMessage);
        }

        // -------------------- Shortcuts -------------------- //

        public static GlobalException validation() {
            return of(GlobalError.VALIDATION_ERROR);
        }

        public static GlobalException internalServer() {
            return of(GlobalError.INTERNAL_SERVER_ERROR);
        }

        public static GlobalException notFound() {
            return of(GlobalError.RESOURCE_NOT_FOUND);
        }


        public static GlobalException forbidden() {
            return of(GlobalError.FORBIDDEN);
        }
    }