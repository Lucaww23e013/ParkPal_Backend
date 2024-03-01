package at.technikum.parkpalbackend.dto.parkdtos;

import at.technikum.parkpalbackend.model.Address;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class CreateParkDto {

    private String parkId;

    @NotBlank(message = "Park name not found. All parks need a name")
    private String name;

    private String description;

    @Embedded
    private Address address;
}