package auth.base.user.model;

import auth.base.user.constant.AppConstant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Auditable<T> extends ReuseId {
    @Id
    @GeneratedValue(generator = AppConstant.ID_GENERATOR_NAME)
    @GenericGenerator(name = AppConstant.ID_GENERATOR_NAME, strategy = AppConstant.ID_GENERATOR_STRATEGY)
    Long id;
    Integer status = 1;
    @CreatedBy
    @Column(nullable = false, updatable = false)
    T createdBy;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    Date createdDate;
    @LastModifiedBy
    @Column(nullable = false)
    T modifiedBy;
    @LastModifiedDate
    @Column(nullable = false)
    Date modifiedDate;
}
