package auth.base.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseListDto<T> {
    T content;
    Long totalElements;
    Integer totalPages;
}
