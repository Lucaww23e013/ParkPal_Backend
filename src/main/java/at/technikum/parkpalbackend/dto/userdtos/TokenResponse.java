package at.technikum.parkpalbackend.dto.userdtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TokenResponse {

    private String token;
}
