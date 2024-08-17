package com.app.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseListDto<T> {
    T content;
    Long totalElements;
    Integer totalPages;
}
