package com.startup.common.dto;

import org.springframework.data.domain.Slice;

import java.util.List;

public record SliceResponse<T>(
        List<T> content,
        boolean hasNext,
        int size
) {
    public static <T> SliceResponse<T> of(List<T> content, boolean hasNext, int size) {
        return new SliceResponse<>(content, hasNext, size);
    }

    public static <T> SliceResponse<T> from(Slice<T> slice) {
        return new SliceResponse<>(slice.getContent(), slice.hasNext(), slice.getSize());
    }
}
