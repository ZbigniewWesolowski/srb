package pl.srb.srb.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.srb.srb.model.Message;
import pl.srb.srb.model.User;
import pl.srb.srb.repository.MessageRepository;
import pl.srb.srb.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class MessageController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;

    @PostMapping("user/sendMessage")
    public String submitReservation(

            @RequestParam Long userId,
            @RequestParam String messageString) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + "1"));

        Message message = new Message();
        message.setUser(user);
        message.setMessage(messageString);
        message.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.of("Europe/Warsaw")).toInstant()));
        messageRepository.save(message);



        return "redirect:/user/home";
    }
}