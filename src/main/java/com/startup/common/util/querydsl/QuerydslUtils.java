package com.startup.common.util.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class QuerydslUtils {

    private QuerydslUtils() {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static OrderSpecifier<?>[] getSort(
            Sort sort,
            Map<String, Expression<?>> sortMap,
            OrderSpecifier<?> defaultSort
    ) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        for (Sort.Order order : sort) {
            Expression<?> expression = sortMap.get(order.getProperty());
            if (expression == null) {
                continue;
            }

            orders.add(new OrderSpecifier(
                    order.isAscending() ? Order.ASC : Order.DESC,
                    expression
            ));
        }

        return orders.isEmpty()
                ? new OrderSpecifier[]{defaultSort}
                : orders.toArray(new OrderSpecifier[0]);
    }

    public static BooleanExpression like(StringPath field, String value) {
        return StringUtils.hasText(value) ? field.containsIgnoreCase(value) : null;
    }

    public static BooleanExpression likeAnyOf(String value, StringPath... fields) {
        if (!StringUtils.hasText(value) || fields == null || fields.length == 0) {
            return null;
        }

        BooleanExpression predicate = null;
        for (StringPath field : fields) {
            if (field == null) {
                continue;
            }

            BooleanExpression expression = field.containsIgnoreCase(value);
            predicate = predicate == null ? expression : predicate.or(expression);
        }

        return predicate;
    }

    public static BooleanExpression eq(NumberPath<Long> field, Long value) {
        return value != null ? field.eq(value) : null;
    }

    public static BooleanExpression eq(StringPath field, String value) {
        return value != null ? field.eq(value) : null;
    }

    public static <E extends Enum<E>> BooleanExpression eq(EnumPath<E> field, E value) {
        return value != null ? field.eq(value) : null;
    }

    public static BooleanExpression goe(NumberPath<Long> field, Long value) {
        return value != null ? field.goe(value) : null;
    }

    public static BooleanExpression loe(NumberPath<Long> field, Long value) {
        return value != null ? field.loe(value) : null;
    }
}
