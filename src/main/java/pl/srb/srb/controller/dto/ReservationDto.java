package pl.srb.srb.controller.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
public class ReservationDto {
        private Long id;
        private String startDate;
        private String startTime;
        private String endTime;
        private String laneName;
        private String userName;
}
