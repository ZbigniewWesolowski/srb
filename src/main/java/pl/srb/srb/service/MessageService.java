package pl.srb.srb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.srb.srb.model.Message;
import pl.srb.srb.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
