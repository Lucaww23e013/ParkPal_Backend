package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.PictureDto;

import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.stereotype.Component;
@Component
public class PictureMapper {

    public UserService userService;

    public PictureDto toDto(Picture picture){
        return PictureDto.builder()
                .pictureId(picture.getPictureId())
                .userId(picture.getUser().getUserId())
                .build();
    }

    public Picture toEntity(PictureDto pictureDto) {
        return Picture.builder()
                .pictureId(pictureDto.getPictureId())
                .user(userService.findByUserId(pictureDto.getUserId()))
                .build();
    }
}
