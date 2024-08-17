// ReservationController.java
package pl.srb.srb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.srb.srb.model.Lane;
import pl.srb.srb.model.Reservation;
import pl.srb.srb.model.User;
import pl.srb.srb.repository.LaneRepository;
import pl.srb.srb.repository.ReservationRepository;
import pl.srb.srb.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Controller
public class ReservationController {

    @Autowired
    private LaneRepository laneRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("user/reservationForm")
    public String showReservationForm(Model model) {
        List<Lane> lanes = laneRepository.findAll();
        List<User> users = userRepository.findAll();
        model.addAttribute("lanes", lanes);
        model.addAttribute("users", users);
        model.addAttribute("hours", getAvailableHours());
        return "user/reservationForm";
    }

    @PostMapping("user/reserve")
    public String submitReservation(

            @RequestParam Long laneId,
            @RequestParam Long userId,
            @RequestParam LocalDate date,
            @RequestParam Integer startHour,
            @RequestParam Integer endHour) {


        if (startHour >= endHour || startHour < 8 || endHour > 18) {
            return "redirect:/user/home"; // Można przekierować na stronę z błędem
        }

        Lane lane = laneRepository.findById(Long.valueOf("1")).orElseThrow(() -> new IllegalArgumentException("Invalid lane Id:" + "1"));
        User user = userRepository.findById(Long.valueOf("1")).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + "1"));

        LocalDateTime startTime = LocalDateTime.of(date, LocalTime.of(startHour, 0));
        LocalDateTime endTime = LocalDateTime.of(date, LocalTime.of(endHour, 0));

        Reservation reservation = new Reservation();
        reservation.setLane(lane);
        reservation.setUser(user);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);

        reservationRepository.save(reservation);

        return "redirect:/user/home";
    }

    private List<Integer> getAvailableHours() {
        return List.of(
                8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18
        );
    }
}
