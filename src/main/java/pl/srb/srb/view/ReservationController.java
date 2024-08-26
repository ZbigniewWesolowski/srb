// ReservationController.java
package pl.srb.srb.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.srb.srb.model.Lane;
import pl.srb.srb.model.Reservation;
import pl.srb.srb.model.User;
import pl.srb.srb.repository.LaneRepository;
import pl.srb.srb.repository.ReservationRepository;
import pl.srb.srb.repository.UserRepository;
import pl.srb.srb.service.LaneService;
import pl.srb.srb.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ReservationController {

    @Autowired
    private LaneRepository laneRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private LaneService laneService;

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


        if (startHour >= endHour || startHour < 9 || endHour > 18) {
            return "redirect:/user/home";
        }

        Lane lane = laneRepository.findById(laneId).orElseThrow(() -> new IllegalArgumentException("Invalid lane Id:" + "1"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + "1"));

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
                9, 10, 11, 12, 13, 14, 15, 16, 17
        );
    }

    @GetMapping("/user/check-availability")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long laneId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Integer startHour,
            @RequestParam Integer endHour) {

        boolean isAvailable = reservationService.isLaneAvailable(laneId, date, startHour, endHour);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/api/availability")
    @ResponseBody
    public Map<Long, Map<LocalDate, Map<Integer, Boolean>>> getAvailability(
            @RequestParam(value = "startDate", required = false) String startDateParam) {
        LocalDate startDate = (startDateParam != null) ? LocalDate.parse(startDateParam) : LocalDate.now();

        Map<Long, Map<LocalDate, Map<Integer, Boolean>>> allLanesAvailability = new HashMap<>();

        List<Lane> lanes = laneService.getAllLanes();

        for (Lane lane : lanes) {
            Map<LocalDate, Map<Integer, Boolean>> availability = new HashMap<>();
            for (int dayOffset = 1; dayOffset < 7; dayOffset++) {
                LocalDate date = startDate.plusDays(dayOffset);
                Map<Integer, Boolean> dailyAvailability = new HashMap<>();
                for (int hour = 9; hour <= 17; hour++) {
                    boolean isAvailable = !reservationService.isLaneReservedOnDateAtHour(lane.getId(), date, hour);
                    dailyAvailability.put(hour, isAvailable);
                }
                availability.put(date, dailyAvailability);
            }
            allLanesAvailability.put(lane.getId(), availability);
        }

        return allLanesAvailability;
    }
    @PostMapping("/user/cancel-reservation")
    public String userCancelReservation(@RequestParam Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return "redirect:/user/home";
    }
    @PostMapping("/admin/cancel-reservation")
    public String adminCancelReservation(@RequestParam Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return "redirect:/admin/upcoming-reservation";
    }

}
