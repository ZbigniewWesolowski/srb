package pl.srb.srb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.srb.srb.model.Lane;
import pl.srb.srb.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
}

