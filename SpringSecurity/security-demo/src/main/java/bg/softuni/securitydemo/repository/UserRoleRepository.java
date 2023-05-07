package bg.softuni.securitydemo.repository;

import bg.softuni.securitydemo.model.entity.UserRoleEntity;
import bg.softuni.securitydemo.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    UserRoleEntity findUserRoleEntityByRole(UserRoleEnum userRoleEnum);
}
