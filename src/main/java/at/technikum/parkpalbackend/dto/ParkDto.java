package at.technikum.parkpalbackend.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ParkDto {

    private String id;

    private String title;
}
