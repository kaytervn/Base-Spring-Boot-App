package com.app.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ABasicAdminDto {
    Long id;
    Integer status;
    Date modifiedDate;
    Date createdDate;
}
