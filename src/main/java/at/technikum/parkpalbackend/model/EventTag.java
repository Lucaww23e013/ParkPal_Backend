package at.technikum.parkpalbackend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder


@Entity
public class EventTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String eventTagId;

    @NotBlank(message = "Event Tag cannot be empty.")
    private String tagName;
}
