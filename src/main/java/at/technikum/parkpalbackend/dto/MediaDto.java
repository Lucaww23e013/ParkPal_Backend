package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.MediaCategory;
import at.technikum.parkpalbackend.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@Builder
@Valid
public class MediaDto {
    @Id
    @UuidGenerator
    private String mediaId;

    private String userId;

    private MediaCategory mediaCategory;

}
