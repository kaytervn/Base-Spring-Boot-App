package com.app.model.criteria;

import com.app.constant.AppConstant;
import com.app.model.Account;
import com.app.model.Group;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AccountCriteria {
    private Long id;
    private Integer kind;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private Long groupId;
    private Integer status;
    private Integer sortDate;

    public Specification<Account> getCriteria() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getId() != null) {
                predicates.add(cb.equal(root.get("id"), getId()));
            }
            if (getKind() != null) {
                predicates.add(cb.equal(root.get("kind"), getKind()));
            }
            if (getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), getStatus()));
            }
            if (StringUtils.isNotBlank(getUsername())) {
                predicates.add(cb.like(cb.lower(root.get("username")), "%" + getUsername().toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(getEmail())) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + getEmail().toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(getPhone())) {
                predicates.add(cb.like(cb.lower(root.get("phone")), "%" + getPhone().toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(getFullName())) {
                predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + getFullName().toLowerCase() + "%"));
            }
            if (getGroupId() != null) {
                Join<Account, Group> joinGroup = root.join("group", JoinType.INNER);
                predicates.add(cb.equal(joinGroup.get("id"), getGroupId()));
            }
            if (getSortDate() != null) {
                query.orderBy(getSortDate().equals(AppConstant.SORT_DATE_ASC) ? cb.asc(root.get("createdDate")) : cb.desc(root.get("createdDate")));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
