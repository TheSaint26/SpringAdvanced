package bg.softuni.securitydemo.service;

import bg.softuni.securitydemo.model.entity.UserEntity;
import bg.softuni.securitydemo.model.entity.UserRoleEntity;
import bg.softuni.securitydemo.model.enums.UserRoleEnum;
import bg.softuni.securitydemo.repository.UserRepository;
import bg.softuni.securitydemo.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitService(UserRoleRepository userRoleRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("${app.default.password}") String defaultPassword) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        initRoles();
        initUsers();
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity moderatorRole = new UserRoleEntity().setRole(UserRoleEnum.MODERATOR);
            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);
            userRoleRepository.save(moderatorRole);
            userRoleRepository.save(adminRole);
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            initAdmin();
            initModerator();
            initNormalUser();
        }
    }

    private void initAdmin() {
        UserEntity admin = new UserEntity()
                .setUsername("Admin")
                .setEmail("admin@example.com")
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setPassword(passwordEncoder.encode("topsecret"))
                .setRoles(userRoleRepository.findAll());
        userRepository.save(admin);
    }
    private void initModerator() {
        UserRoleEntity moderatorRole = userRoleRepository.findUserRoleEntityByRole(UserRoleEnum.MODERATOR);
        UserEntity moderator = new UserEntity()
                .setUsername("Moderator")
                .setEmail("moderator@example.com")
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setPassword(passwordEncoder.encode("topsecret"))
                .setRoles(List.of(moderatorRole));
        userRepository.save(moderator);
    }

    private void initNormalUser() {
        UserEntity normalUser = new UserEntity()
                .setUsername("User")
                .setEmail("user@example.com")
                .setFirstName("User")
                .setLastName("Userov")
                .setPassword(passwordEncoder.encode("topsecret"));
        userRepository.save(normalUser);
    }
}
