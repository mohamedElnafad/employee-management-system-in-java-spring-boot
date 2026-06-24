package com.quickStart.quickStart.DTOs;

import java.util.List;

public record PaginationResponse<T>(
        List<T> content,
        int currentPage,
        int totalPages,
        int pageSize,
        long totalItems,
        Boolean hasNextPage,
        Boolean hasPreviousPage,
        String nextPageURL,
        String previousPageURL


) {

}
