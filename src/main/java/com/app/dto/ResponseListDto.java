package com.app.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListDto<T> {
    private T content;
    private Long totalElements;
    private Integer totalPages;
}
