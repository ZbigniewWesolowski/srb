package pl.srb.srb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.srb.srb.model.Lane;

public interface LaneRepository extends JpaRepository<Lane, Long> {
    // Możesz dodać dodatkowe metody, jeśli są potrzebne
}