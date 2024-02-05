package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class Media{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String mediaId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotEmpty(message="Media must belong to a User. Pls add a User")
    private User user;

    @Enumerated(EnumType.STRING)
    private MediaCategory mediaCategory;


}
