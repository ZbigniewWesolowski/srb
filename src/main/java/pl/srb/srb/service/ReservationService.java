package pl.srb.srb.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.srb.srb.model.Lane;
import pl.srb.srb.model.Reservation;
import pl.srb.srb.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<String> getAvailableHours(Long laneId) {
        return List.of("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00");
    }

    public void reserve(Long laneId, String hour, String name, String email) {
    }

    public boolean isLaneAvailable(Long laneId, LocalDate date, Integer startHour, Integer endHour) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.of(startHour, 0));
        LocalDateTime end = LocalDateTime.of(date, LocalTime.of(endHour, 0));
        return reservationRepository.findConflictingReservations(laneId, start, end).isEmpty();

    }

    public boolean isLaneReservedOnDateAtHour(Long laneId, LocalDate date, int hour) {
        LocalDateTime startTime = date.atTime(hour, 0);
        LocalDateTime endTime = date.atTime(hour + 1, 0);

        Lane lane = new Lane();
        lane.setId(laneId);

        return reservationRepository.existsByLaneAndStartTimeLessThanAndEndTimeGreaterThan(lane, endTime, startTime);
    }

    public List<Reservation> getPastReservationsForUser(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findByUserIdAndStartTimeBefore(userId, now);
    }

    public void cancelReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public List<Reservation> getUpcomingReservationsForUser(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findByUserIdAndEndTimeAfter(userId, now);
    }

    public List<Reservation> getAllUpcomingReservations () {
        LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findAllByEndTimeAfter(now);
    }

    public List<Reservation> getAllPastReservations () {
        LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findAllByEndTimeBefore(now);
    }
}
