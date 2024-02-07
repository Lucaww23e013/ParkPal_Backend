package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.Address;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Media;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Builder
@Valid
public class ParkDto {
    private String parkId;

    @NotBlank(message = "Park name not found. All parks need a name")
    @UniqueElements(message = "Park name already exists. Park must have unique name, pls choose different Name.")
    private String parkName;

    private String description;

    @Embedded
    private Address parkAddress;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Event> parkEvents = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Media> parkMedia = new ArrayList<>();

    public ParkDto(String parkId, String parkName, String description, Address parkAddress, List<Event> parkEvents, List<Media> parkMedia) {
        this.parkId = parkId;
        this.parkName = parkName;
        this.description = description;
        this.parkAddress = parkAddress;
        this.parkEvents = parkEvents;
        this.parkMedia = parkMedia;
    }

}
