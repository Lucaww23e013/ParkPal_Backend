package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.PictureDto;

import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.service.PictureService;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PictureMapper {

    public UserService userService;
    public PictureService pictureService;

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
                .uploadDate(pictureDto.getUploadDate())
                .build();
    }

    public Picture fromMultipartFileToEntity(MultipartFile file){
        return Picture.builder().build();
        // add logic later according to usage;
        // maybe change Picture to MultipartFileType;
    }
}
