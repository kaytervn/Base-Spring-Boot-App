package com.app.model;

import com.app.constant.AppConstant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "db_app_setting")
@EntityListeners(AuditingEntityListener.class)
public class Setting extends Auditable<String> {
    @Id
    @GeneratedValue(generator = AppConstant.APP_ID_GENERATOR_NAME)
    @GenericGenerator(name = AppConstant.APP_ID_GENERATOR_NAME, strategy = AppConstant.APP_ID_GENERATOR_STRATEGY)
    private Long id;
    private String groupName;
    @Column(unique = true)
    private String keyName;
    @Column(columnDefinition = "text")
    private String valueData;
    private String dataType; // Integer, String, Boolean, Double, RichText
    private Boolean isSystem;
}
