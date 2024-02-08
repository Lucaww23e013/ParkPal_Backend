package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
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
public class Media {

    @Id
    @UuidGenerator
    @Column(name = "media_id")
    private String mediaId;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    @NotNull(message="Media must belong to a User. Pls add a User")
    private User user;

    @Enumerated(EnumType.STRING)
    private MediaCategory mediaCategory;

//    public Media(String mediaId, User user, MediaCategory mediaCategory) {
//        this.mediaId = mediaId;
//        this.user = user;
//        this.mediaCategory = mediaCategory;
//    }

}
