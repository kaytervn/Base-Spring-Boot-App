package com.app.model.criteria;

import com.app.constant.AppConstant;
import com.app.model.Setting;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class SettingCriteria {
    private Long id;
    private String groupName;
    private String keyName;
    private Boolean isSystem;
    private Integer status;
    private Integer sortDate;

    public Specification<Setting> getCriteria() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getId() != null) {
                predicates.add(cb.equal(root.get("id"), getId()));
            }
            if (StringUtils.isNoneBlank(getGroupName())) {
                predicates.add(cb.like(cb.lower(root.get("groupName")), "%" + getGroupName().toLowerCase() + "%"));
            }
            if (StringUtils.isNoneBlank(getKeyName())) {
                predicates.add(cb.like(cb.lower(root.get("keyName")), "%" + getKeyName().toLowerCase() + "%"));
            }
            if (getIsSystem() != null) {
                predicates.add(cb.equal(root.get("isSystem"), getIsSystem()));
            }
            if (getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), getStatus()));
            }
            if (getSortDate() != null) {
                query.orderBy(getSortDate().equals(AppConstant.SORT_DATE_ASC) ? cb.asc(root.get("createdDate")) : cb.desc(root.get("createdDate")));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
