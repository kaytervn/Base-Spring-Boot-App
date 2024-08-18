package com.app.model.criteria;

import com.app.model.Account;
import com.app.model.Group;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCriteria {
    Long id;
    Integer kind;
    String username;
    String email;
    String fullName;
    String phone;
    Long groupId;
    Integer status;

    public Specification<Account> getCriteria() {
        return new Specification<Account>() {
            @Override
            public Predicate toPredicate(@NonNull Root<Account> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
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
                if (StringUtils.isNoneBlank(getUsername())) {
                    predicates.add(cb.like(cb.lower(root.get("username")), "%" + getUsername().toLowerCase() + "%"));
                }
                if (StringUtils.isNoneBlank(getEmail())) {
                    predicates.add(cb.like(cb.lower(root.get("email")), "%" + getEmail().toLowerCase() + "%"));
                }
                if (StringUtils.isNoneBlank(getFullName())) {
                    predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + getFullName().toLowerCase() + "%"));
                }
                if (getGroupId() != null) {
                    Join<Account, Group> joinGroup = root.join("group", JoinType.INNER);
                    predicates.add(cb.equal(joinGroup.get("id"), getGroupId()));
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
