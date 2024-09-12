package at.technikum.parkpalbackend.dto.userdtos;

import at.technikum.parkpalbackend.model.enums.Role;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String id;
    private Role role;
}