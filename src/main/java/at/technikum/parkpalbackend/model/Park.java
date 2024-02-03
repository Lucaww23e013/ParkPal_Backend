package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class Park {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "parkId not found. All parks need to have an parkId")
    private String parkId;

    @NotEmpty(message="Park name not found. All parks need a name")
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "address_address_id")
    private Address parkAddress;

    @OneToMany
    private List<Event> parkEvents;

    @OneToMany
    private List<Media> parkMedia;


}
