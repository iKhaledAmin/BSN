    package com.khaled_amin.book_social_network.identity.core.model;

    import com.khaled_amin.book_social_network.core.exception.validation.ValidationException;
    import com.khaled_amin.book_social_network.identity.core.exception.IdentityValidationException;
    import lombok.Getter;

    @Getter
    public enum ActorType {

        ACCOUNT("ACC", ActorCategory.DOMAIN),
        CLIENT("CLI", ActorCategory.DOMAIN),

        SYSTEM("SYS", ActorCategory.TECHNICAL),
        ANONYMOUS("ANO", ActorCategory.TECHNICAL); // later may be DOMAIN

        private final String codePrefix;
        private final ActorCategory category;

        ActorType(String codePrefix, ActorCategory category) {
            this.codePrefix = codePrefix;
            this.category = category;
        }


        public boolean isDomainActor() {
            return category == ActorCategory.DOMAIN;
        }

        public boolean isTechnicalActor() {
            return category == ActorCategory.TECHNICAL;
        }


        /**
         * Converts the given value to {@link ActorType}.
         *
         * <p>Performs strict matching using {@link Enum#valueOf(Class, String)}.
         * Callers should handle and translate exceptions if the input is external.</p>
         *
         * @param value {@link String} raw actor type value
         * @return corresponding {@link ActorType}
         * @throws ValidationException if value is null, blank, or unsupported
         */
        public static ActorType from(String value) {

            if (value == null || value.isBlank()) {
                throw IdentityValidationException.invalidActorType()
                        .withDebugDetails("receivedValue", value);
            }

            try {
                return ActorType.valueOf(value.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw IdentityValidationException.invalidActorType(ex)
                        .withDebugDetails("receivedValue", value);
            }
        }
    }