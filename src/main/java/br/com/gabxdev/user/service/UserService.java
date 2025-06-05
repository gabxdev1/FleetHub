package br.com.gabxdev.user.service;

import br.com.gabxdev.core.exception.EmailAlreadyExistsException;
import br.com.gabxdev.core.exception.NotFoundException;
import br.com.gabxdev.user.domain.User;
import br.com.gabxdev.user.projection.LoginResponse;
import br.com.gabxdev.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        validateEmail(user.getEmail());

        return userRepository.save(user);
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("E-mail %s already exists".formatted(email));
        }
    }

    public User findUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id %s not found".formatted(userId)));
    }

    public LoginResponse findUserIdByEmailOrThrow(String email) {
        return userRepository.findIdByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
