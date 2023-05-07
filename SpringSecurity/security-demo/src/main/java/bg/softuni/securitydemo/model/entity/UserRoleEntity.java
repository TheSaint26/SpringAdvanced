package bg.softuni.securitydemo.model.entity;

import bg.softuni.securitydemo.model.enums.UserRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoleEntity extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public UserRoleEntity() {
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
