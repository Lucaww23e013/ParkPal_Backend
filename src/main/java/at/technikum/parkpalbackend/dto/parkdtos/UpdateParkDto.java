package at.technikum.parkpalbackend.dto.parkdtos;

import at.technikum.parkpalbackend.model.Address;
import jakarta.persistence.Embedded;
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
public class UpdateParkDto {

    @NotBlank(message = "Park name not found. All parks need a name")
    private String name;

    @NotBlank(message = "Park description not found. All parks need a description")
    private String description;

    @Embedded
    private Address address;

    @ToString.Exclude
    @Builder.Default
    private List<String> eventIds = new ArrayList<>();

    @ToString.Exclude
    @Builder.Default
    private List<String> filesExternalIds = new ArrayList<>();
}
