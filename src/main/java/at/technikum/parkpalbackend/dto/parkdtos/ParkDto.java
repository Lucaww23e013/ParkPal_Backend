package at.technikum.parkpalbackend.dto.parkdtos;

import at.technikum.parkpalbackend.model.Address;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Media;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.UUID;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

@Builder
@Valid
public class ParkDto {
    @Id
    @UuidGenerator
    private String parkId;

    @NotBlank(message = "Park name not found. All parks need a name")
    private String name;

    private String description;

    @Embedded
    private Address address;

    // @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Event> parkEvents = new ArrayList<>();

    //@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Media> parkMedia = new ArrayList<>();


}
