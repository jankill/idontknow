package com.idontknow.business.application.services.system.query;

/**
 * This is a model class for SearchOrdersRequest type.
 */
public record SearchDictionariesRequest(
        SearchDictionariesFilter filter,
        DefaultSearchSort sort,
        int number,
        int size) {
}
