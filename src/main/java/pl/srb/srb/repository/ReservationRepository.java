package pl.srb.srb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.srb.srb.model.Lane;
import pl.srb.srb.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    @Query("SELECT r FROM Reservation r WHERE r.lane.id = :laneId AND r.startTime < :endTime AND r.endTime > :startTime")
    List<Reservation> findConflictingReservations(@Param("laneId") Long laneId,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);

    boolean existsByLaneAndStartTimeLessThanAndEndTimeGreaterThan(
            Lane lane, LocalDateTime startTime, LocalDateTime endTime);


        List<Reservation> findByUserIdAndStartTimeBefore(Long userId, LocalDateTime endTime);

    List<Reservation> findByUserIdAndEndTimeAfter(Long userId, LocalDateTime now);
}
