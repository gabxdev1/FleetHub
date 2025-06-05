package br.com.gabxdev.user.repository;

import br.com.gabxdev.user.domain.User;
import br.com.gabxdev.user.projection.LoginResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    @Query("""
            SELECT new br.com.gabxdev.user.projection.LoginResponse(acc.id, acc.password)
            FROM User acc
            WHERE LOWER(acc.email) = LOWER(:email)
            """)
    Optional<LoginResponse> findIdByEmailIgnoreCase(String email);
}
