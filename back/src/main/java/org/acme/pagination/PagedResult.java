package org.acme.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResult<T> {
    private List<T> data;
    private Long totalElements;
    private int totalPages;
    private boolean hasPrevious;
    private boolean hasNext;

    public PagedResult(List<T> data, Long totalElements, int totalPages, boolean hasPrevious, boolean hasNext) {
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
    }

    // Getters and setters
}