package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.enums.FileType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class FileDto {

    private String externalId;

    private String filename;

    private String userId;

    private String evenId;

    private String parkId;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private LocalDateTime uploadDate;
}
