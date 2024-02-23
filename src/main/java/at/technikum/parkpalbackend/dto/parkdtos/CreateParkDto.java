package at.technikum.parkpalbackend.dto.parkdtos;

import at.technikum.parkpalbackend.model.Address;
import jakarta.persistence.Column;
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
@Builder
@Valid
public class CreateParkDto {
    @Id
    @UuidGenerator
    private String parkId;

    @NotBlank(message = "Park name not found. All parks need a name")
    private String parkName;

    private String description;

    @Embedded
    private Address parkAddress;

    public CreateParkDto(String parkId, String parkName, String description, Address parkAddress) {
        this.parkId = parkId;
        this.parkName = parkName;
        this.description = description;
        this.parkAddress = parkAddress;
    }

}