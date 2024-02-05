package at.technikum.parkpalbackend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder


@Entity
public class EventTag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String eventTagId;


    @NotBlank(message = "Event Tag cannot be empty.")
    private String tagName;
}
