package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.PictureDto;

import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.service.UploadService;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class PictureMapper {

    public UserService userService;
    public UploadService uploadService;

    public PictureDto toDto(Picture picture){
        return PictureDto.builder()
                .id(picture.getId())
                .userId(picture.getUser().getId())
                .build();
    }

    public Picture toEntity(PictureDto pictureDto) {
        return Picture.builder()
                .id(pictureDto.getId())
                .user(userService.findByUserId(pictureDto.getUserId()))
                .uploadDate(pictureDto.getUploadDate())
                .build();
    }

    public Picture fromMultipartFileToEntity(MultipartFile file) throws IOException {
        return Picture.builder()
                .file(uploadService.transferToBytes(file)).build();
        // add logic later according to usage;
        // maybe change Picture to MultipartFileType;
    }
}
