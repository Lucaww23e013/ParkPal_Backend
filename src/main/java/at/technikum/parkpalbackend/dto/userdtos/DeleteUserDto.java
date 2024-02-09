package at.technikum.parkpalbackend.dto.userdtos;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DeleteUserDto {

    private String userId;
}
