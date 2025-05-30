package br.com.gabxdev.iam.repository;

import br.com.gabxdev.iam.domain.IamUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<IamUser, Long> {

    Optional<IamUser> findUserByEmailIgnoreCase(String email);
}
