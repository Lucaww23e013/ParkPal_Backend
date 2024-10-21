package at.technikum.parkpalbackend.dto.eventdtos;

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
    @Length(min = 3, max = 250, message = "Event title should have at least 3 characters" +
            "and should not exceed 250 characters.")
    private String title;

    @NotBlank(message = "Event description not Valid")
    @Length(max = 1000, message = "Event Description must not exceed 1000 characters")
    private String description;

    @Timestamp
    @NotNull(message="Event Start Time not found. All Events need to have a Start and End Time")
    private LocalDateTime startTS;

    @Timestamp
    @NotNull(message="Event End Time not found. All Events need to have a Start and End Time")
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
