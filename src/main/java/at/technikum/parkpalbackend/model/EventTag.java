package at.technikum.parkpalbackend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;


@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class EventTag {

    @Id
    @UuidGenerator
    @Column(name = "event_tag_id")
    private String eventTagId;

    @NotBlank(message = "Event Tag cannot be empty.")
    private String name;


}
