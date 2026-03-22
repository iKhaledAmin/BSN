package com.khaled_amin.book_social_network.common.utils.diff;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


public final class DiffUtils {

    private DiffUtils() {
        // prevent instantiation
    }


    public static <T, ID> DiffResult<T> diff(Collection<T> current,Collection<T> incoming,Function<T, ID> idExtractor) {

        Objects.requireNonNull(current, "current collection must not be null");
        Objects.requireNonNull(incoming, "incoming collection must not be null");
        Objects.requireNonNull(idExtractor, "idExtractor must not be null");

        // Build lookup sets (O(1))
        Set<ID> currentIds = current.stream()
                .map(idExtractor)
                .collect(Collectors.toSet());

        Set<ID> incomingIds = incoming.stream()
                .map(idExtractor)
                .collect(Collectors.toSet());

        // Compute ADD → incoming - current
        List<T> toAdd = incoming
                .stream()
                .filter(item -> !currentIds.contains(idExtractor.apply(item)))
                .toList();

        // Compute REMOVE → current - incoming
        List<T> toRemove = current
                .stream()
                .filter(item -> !incomingIds.contains(idExtractor.apply(item)))
                .toList();

        return new DiffResult<>(toAdd, toRemove);
    }
}