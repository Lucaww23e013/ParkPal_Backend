package at.technikum.parkpalbackend.dto.eventdtos;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Component
public class DeleteEventDto {

    private String eventId;

}
