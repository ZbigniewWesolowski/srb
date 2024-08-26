package pl.srb.srb.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.srb.srb.controller.dto.ReservationDto;
import pl.srb.srb.controller.mapper.ReservationDtoMapper;
import pl.srb.srb.model.Message;
import pl.srb.srb.model.Reservation;
import pl.srb.srb.model.User;
import pl.srb.srb.service.MessageService;
import pl.srb.srb.service.ReservationService;
import pl.srb.srb.service.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class AdminController {

    @Autowired
    private ReservationDtoMapper reservationDtoMapper;
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping("admin/home")
    public String getAllMessages (Model model) {
        List<Message> messagesList = messageService.getAllMessages();
        Collections.sort(messagesList, new Comparator<Message>() {
            @Override
            public int compare(Message m1, Message m2) {
                return m2.getDate().compareTo(m1.getDate());            }
        });
        model.addAttribute("messages", messagesList);
        return "admin/home";
    }

    @GetMapping("admin/upcoming-reservation")
    public String getUpcomingReservations (Model model) {
        List<Reservation> reservationList = reservationService.getAllUpcomingReservations();
        List<ReservationDto> reservationDtoList = reservationList.stream()
                .map(reservationDtoMapper::mappingToDto)
                .collect(Collectors.toList());
        model.addAttribute("reservationDtoList", reservationDtoList);

        return "admin/upcoming-reservations";
    }

    @GetMapping("admin/past-reservations")
    public String getPastReservations (Model model) {
        List<Reservation> reservationList = reservationService.getAllPastReservations();
        List<ReservationDto> reservationDtoList = reservationList.stream()
                .map(reservationDtoMapper::mappingToDto)
                .collect(Collectors.toList());
        model.addAttribute("reservationDtoList", reservationDtoList);

        return "admin/past-reservations";
    }

    @GetMapping ("admin/users")
    public String getUsers (Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userlist", userList);
        return "admin/users";
    }


}
