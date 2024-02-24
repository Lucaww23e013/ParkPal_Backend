package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.MediaDto;

import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.stereotype.Component;
@Component
public class MediaMapper {

    public UserService userService;

    public MediaDto toDto(Media media){
        return MediaDto.builder()
                .mediaId(media.getMediaId())
                .userId(media.getUser().getUserId())
                .build();
    }

    public Media toEntity(MediaDto mediaDto) {
        return Media.builder()
                .mediaId(mediaDto.getMediaId())
                .user(userService.findByUserId(mediaDto.getUserId()))
                .build();
    }
}
