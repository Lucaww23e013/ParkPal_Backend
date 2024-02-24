package at.technikum.parkpalbackend.dto.parkdtos;

import at.technikum.parkpalbackend.model.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class CreateParkDto {
    @Id
    @UuidGenerator
    private String parkId;

    @NotBlank(message = "Park name not found. All parks need a name")
    private String name;

    private String description;

    @Embedded
    private Address address;


}