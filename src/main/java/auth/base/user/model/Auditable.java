package auth.base.user.model;

import auth.base.user.constant.AppConstant;
import auth.base.user.service.id.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Auditable<T> extends ReuseId {
    @Id
    @GeneratedValue(generator = AppConstant.ID_GENERATOR)
    @GenericGenerator(name = AppConstant.ID_GENERATOR, type = IdGenerator.class)
    Long id;
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    T createdBy;
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    Date createdDate;
    @LastModifiedBy
    @Column(name = "modified_by", nullable = false)
    T modifiedBy;
    @LastModifiedDate
    @Column(name = "modified_date", nullable = false)
    Date modifiedDate;
    int status = 1;
}
