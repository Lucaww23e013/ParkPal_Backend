package at.technikum.parkpalbackend.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@Builder
public class ParkDto {

    private String id;

    private String title;
}
