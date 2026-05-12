package com.startup.common.util.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;

import java.time.LocalDateTime;

// QueryDSL cursor pagination에서 반복되는 limit, cursor 조건, 정렬 생성을 모아둔 유틸이다.
public final class CursorSliceUtils {

    private CursorSliceUtils() {
    }

    public static int resolveLimit(Integer size, int defaultSize, int maxSize) {
        if (size == null || size < 1) {
            return defaultSize;
        }
        return Math.min(size, maxSize);
    }

    public static int resolveOffset(Integer offset) {
        return offset == null || offset < 0 ? 0 : offset;
    }

    public static BooleanExpression ltCursor(NumberPath<Long> idPath, Long cursor) {
        return cursor != null ? idPath.lt(cursor) : null;
    }

    public static BooleanExpression gtCursor(NumberPath<Long> idPath, Long cursor) {
        return cursor != null ? idPath.gt(cursor) : null;
    }

    public static BooleanExpression ltCursor(DateTimePath<LocalDateTime> dateTimePath, LocalDateTime cursor) {
        return cursor != null ? dateTimePath.lt(cursor) : null;
    }

    public static BooleanExpression ltCompositeCursor(
            NumberExpression<Long> scoreExpression,
            Long scoreCursor,
            NumberPath<Long> idPath,
            Long idCursor
    ) {
        // 정렬 기준 값이 같은 row는 id를 보조 cursor로 사용해 중복/누락을 줄인다.
        if (scoreCursor == null || idCursor == null) {
            return null;
        }

        return scoreExpression.lt(scoreCursor)
                .or(scoreExpression.eq(scoreCursor).and(idPath.lt(idCursor)));
    }

    public static OrderSpecifier<Long> orderByIdDesc(NumberPath<Long> idPath) {
        return new OrderSpecifier<>(Order.DESC, idPath);
    }

    public static OrderSpecifier<Long> orderByIdAsc(NumberPath<Long> idPath) {
        return new OrderSpecifier<>(Order.ASC, idPath);
    }

    public static OrderSpecifier<Long> orderByScoreDesc(NumberExpression<Long> scoreExpression) {
        return new OrderSpecifier<>(Order.DESC, scoreExpression);
    }
}
