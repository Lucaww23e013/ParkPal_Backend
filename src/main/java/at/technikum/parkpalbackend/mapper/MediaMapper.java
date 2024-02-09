package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.MediaDto;

import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.User;
import org.springframework.stereotype.Component;
@Component
public class MediaMapper {

    public MediaDto toDto(Media media){
        return new MediaDto(
                media.getMediaId(),
                media.getUser().getUserId(),
                media.getMediaCategory()
        );
    }

    public Media toEntity(MediaDto mediaDto) {
        return new Media(
                mediaDto.getMediaId(),
                new User(mediaDto.getUserId(), null, null,null,null,null,null,null,null,false,null,null),
                mediaDto.getMediaCategory()
        );
    }
}
