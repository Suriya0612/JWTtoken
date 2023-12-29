package jwt.spring.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jwt.spring.security.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
		Optional<User> findByUsername(String username);
}
