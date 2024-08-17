package pl.srb.srb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.srb.srb.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Możesz dodać dodatkowe metody, jeśli są potrzebne
}