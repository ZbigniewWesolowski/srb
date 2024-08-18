package pl.srb.srb.controller.mapper;

import org.springframework.stereotype.Service;
import pl.srb.srb.controller.dto.ReservationDto;
import pl.srb.srb.model.Reservation;
import java.time.format.DateTimeFormatter;

@Service
public class ReservationDtoMapper {
    public ReservationDto mappingToDto (Reservation reservation) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        return ReservationDto.builder()
                .id(reservation.getId())
                .startDate(reservation.getStartTime().format(formatterDate))
                .startTime(reservation.getStartTime().format(formatterTime))
                .endTime(reservation.getEndTime().format(formatterTime))
                .laneName(reservation.getLane().getName())
                .build();
    }
}
