package at.technikum.parkpalbackend.dto.eventdtos;

import at.technikum.parkpalbackend.dto.UpperLimit;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Component
public class EventDto {

    private String id;

    @NotBlank(message = "Event title not Valid")
    private String title;

    @NotBlank(message = "Event description not Valid")
    @Length(max = 1000, message = "Event Description must not exceed 1000 characters")
    private String description;

    @Timestamp
    @FutureOrPresent(message = "Start EventTime must be now or in the Future")
    @NotNull(message="Event Start Time not found. All Events need to have a Start and End Time")
    @UpperLimit(message = "Event Start Time must be at least 1 hour in the Future", years = 1)
    private LocalDateTime startTS;

    @Timestamp
    @FutureOrPresent(message = "End EventTime must be now or in the Future")
    @NotNull(message="Event End Time not found. All Events need to have a Start and End Time")
    @UpperLimit(message = "Event End Time must be at least 1 hour in the Future", years = 1)
    private LocalDateTime endTS;

    @NotNull(message = "Park not found. All Events need to take place in a Park")
    private String parkId;

    @NotNull(message = "Creator not found. All Events need to have been created by an User")
    private String creatorUserId;

    private String creatorName;

    @Builder.Default
    private List<String> joinedUserIds = new ArrayList<>();

    @Builder.Default
    private List<String> joinedUserNames = new ArrayList<>();

    @Builder.Default
    private List<String> mediaFileExternalIds = new ArrayList<>();

    @Builder.Default
    private Set<String> eventTagsIds = new HashSet<>();
}
