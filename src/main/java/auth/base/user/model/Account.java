package auth.base.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "db_base_account")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends Auditable<String> {
    Integer kind;
    String username;
    String phone;
    String email;
    @JsonIgnore
    String password;
    String fullName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    Group group;
    Date lastLogin;
    String avatarPath;
    String resetPasswordCode;
    Date resetPasswordTime;
    Integer attemptCode;
    Integer attemptLogin;
    Boolean isSuperAdmin = false;
}
