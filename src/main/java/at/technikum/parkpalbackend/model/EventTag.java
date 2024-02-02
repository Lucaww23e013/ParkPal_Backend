package at.technikum.parkpalbackend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder


@Entity
public class EventTag {
    @Id
    @UuidGenerator
    private String eventTagId;

    @NotBlank(message = "Event Tag cannot be empty.")
    private String tagName;
}
