package at.technikum.parkpalbackend.dto.parkdtos;

import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.model.Address;
import at.technikum.parkpalbackend.model.File;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    private String id;

    @NotBlank(message = "Park name not found. All parks need a name")
    private String name;

    private String description;

    @Embedded
    private Address address;

    @ToString.Exclude
    private List<EventDto> parkEventDtos = new ArrayList<>();

    @ToString.Exclude
    private List<File> parkFiles = new ArrayList<>();
}
