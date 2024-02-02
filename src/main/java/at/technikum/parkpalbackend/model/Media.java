package at.technikum.parkpalbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class Media{
    @Id
    @UuidGenerator
    private String mediaId;

    @NotEmpty(message="Media must belong to a User. Pls add a User")
    private User user;

    @NotEmpty(message ="Media Type empty. Pls add a Media Type")
    private MediaCategory mediaCategory;


}
