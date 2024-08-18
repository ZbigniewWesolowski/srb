//package pl.srb.srb.controller;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import pl.srb.srb.service.ReservationService;
//
//import java.time.LocalDate;
//import java.util.*;
//
//@RestController
//@RequestMapping("/api")
//public class ReservationRestController {
//
//    // Serwis odpowiedzialny za operacje na rezerwacjach
//    private final ReservationService reservationService;
//
//    public ReservationController(ReservationService reservationService) {
//        this.reservationService = reservationService;
//    }
//
//    @GetMapping("/availability")
//    public ResponseEntity<Map<String, Map<Integer, Boolean>>> getLaneAvailability(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
//
//        Map<String, Map<Integer, Boolean>> availability = new HashMap<>();
//
//        for (int dayOffset = 0; dayOffset < 7; dayOffset++) {
//            LocalDate date = startDate.plusDays(dayOffset);
//            Map<Integer, Boolean> dailyAvailability = new HashMap<>();
//
//            for (int hour = 8; hour < 19; hour++) {
//                boolean isAvailable = !reservationService.isLaneReservedOnDateAtHour(date, hour);
//                dailyAvailability.put(hour, isAvailable);
//            }
//
//            availability.put(date.toString(), dailyAvailability);
//        }
//
//        return ResponseEntity.ok(availability);
//    }
//}
