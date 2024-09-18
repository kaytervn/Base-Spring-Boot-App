package com.app.model.criteria;

import com.app.constant.AppConstant;
import com.app.model.Group;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GroupCriteria {
    private Long id;
    private String name;
    private Integer kind;
    private Integer status;
    private Integer sortDate;

    public Specification<Group> getCriteria() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getId() != null) {
                predicates.add(cb.equal(root.get("id"), getId()));
            }
            if (StringUtils.isNotBlank(getName())) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
            }
            if (getKind() != null) {
                predicates.add(cb.equal(root.get("kind"), getKind()));
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
