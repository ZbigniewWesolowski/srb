package pl.srb.srb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.srb.srb.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    // Możesz dodać dodatkowe metody, jeśli są potrzebne
}