package pl.srb.srb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.srb.srb.model.Lane;
import pl.srb.srb.service.LaneService;
import pl.srb.srb.service.ReservationService;

import java.util.List;

@Controller
public class LaneController {

    @Autowired
    private LaneService laneService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/lanes")
    public String listLanes(Model model) {
        List<Lane> lanes = laneService.findAll();
        model.addAttribute("lanes", lanes);
        return "lane-list";
    }

    @GetMapping("/hours/{laneId}")
    public String viewHours(@PathVariable Long laneId, Model model) {
        Lane lane = laneService.findById(laneId);
        List<String> hours = reservationService.getAvailableHours(laneId);
        model.addAttribute("lane", lane);
        model.addAttribute("hours", hours);
        return "hour-list";
    }

    @GetMapping("/reserve/{laneId}/{hour}")
    public String reserveForm(@PathVariable Long laneId, @PathVariable String hour, Model model) {
        Lane lane = laneService.findById(laneId);
        model.addAttribute("lane", lane);
        model.addAttribute("hour", hour);
        return "reservation-form";
    }

    @PostMapping("/reserve")
    public String makeReservation(@RequestParam Long laneId, @RequestParam String hour,
                                  @RequestParam String name, @RequestParam String email) {
        reservationService.reserve(laneId, hour, name, email);
        return "redirect:/lanes";
    }
}
