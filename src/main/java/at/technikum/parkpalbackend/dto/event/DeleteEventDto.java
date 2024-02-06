package at.technikum.parkpalbackend.dto.event;

import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.model.User;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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
