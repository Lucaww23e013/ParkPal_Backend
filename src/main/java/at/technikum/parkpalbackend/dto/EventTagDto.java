package at.technikum.parkpalbackend.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EventTagDto {

    private String eventTagId;

    private String tagName;

}

