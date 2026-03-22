package com.khaled_amin.book_social_network.common.utils.diff;

import java.util.Collections;
import java.util.List;

public class DiffResult<T> {

    private final List<T> toAdd;
    private final List<T> toRemove;

    public DiffResult(List<T> toAdd, List<T> toRemove) {
        this.toAdd = Collections.unmodifiableList(toAdd);
        this.toRemove = Collections.unmodifiableList(toRemove);
    }


    public List<T> getToAdd() {
        return toAdd;
    }

    public List<T> getToRemove() {
        return toRemove;
    }

    public boolean isEmpty() {
        return toAdd.isEmpty() && toRemove.isEmpty();
    }

    public boolean hasChanges() {
        return !isEmpty();
    }
}