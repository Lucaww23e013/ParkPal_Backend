package at.technikum.parkpalbackend.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EventTagDto {

    private String eventTagId;

    private String tagName;

}

