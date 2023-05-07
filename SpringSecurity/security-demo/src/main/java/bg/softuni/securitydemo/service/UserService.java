package bg.softuni.securitydemo.service;

import bg.softuni.securitydemo.model.dto.UserRegistrationDTO;
import bg.softuni.securitydemo.model.entity.UserEntity;
import bg.softuni.securitydemo.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public void registerUser(UserRegistrationDTO userRegistrationDTO,
                             Consumer<Authentication> successfulLoginProcessor) {
        UserEntity user = new UserEntity()
                .setUsername(userRegistrationDTO.getUsername())
                .setEmail(userRegistrationDTO.getEmail())
                .setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()))
                .setFirstName(userRegistrationDTO.getFirstName())
                .setLastName(userRegistrationDTO.getLastName());
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        successfulLoginProcessor.accept(authentication);
    }
}
