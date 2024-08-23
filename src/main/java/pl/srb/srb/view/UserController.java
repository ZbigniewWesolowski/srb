package pl.srb.srb.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import pl.srb.srb.controller.dto.ReservationDto;
import pl.srb.srb.controller.mapper.ReservationDtoMapper;
import pl.srb.srb.model.Reservation;
import pl.srb.srb.service.ReservationService;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationDtoMapper reservationDtoMapper;


    @GetMapping("user/user-history")
//    public String userHistory(Model model, @RequestParam("userId") Long userId) {
    public String userHistory(Model model) {
        List<Reservation> pastReservations = reservationService.getPastReservationsForUser(Long.valueOf(Integer.valueOf("1")));
        List<ReservationDto> pastReservationsDtos = pastReservations.stream()
                .map(reservationDtoMapper::mappingToDto)
                .collect(Collectors.toList());

        model.addAttribute("pastReservationsDtos", pastReservationsDtos);
        return "user/user-history";}


    @GetMapping("/user/home")
    public String getUpcomingReservations(Model model) {
        Long userId = Long.valueOf("1"); // Można zmienić na dynamiczne pobieranie
        List<Reservation> upcomingReservations = reservationService.getUpcomingReservationsForUser(userId);
        List<ReservationDto> upcomingReservationsDtos = upcomingReservations.stream()
                .map(reservationDtoMapper::mappingToDto)
                .collect(Collectors.toList());

        model.addAttribute("upcomingReservations", upcomingReservationsDtos);
        return "user/home"; // Nazwa widoku, który wyświetla nadchodzące rezerwacje
    }

    @GetMapping("/user/user-contact")
    public String userContact(){
        return "user/user-contact";
    }


}


