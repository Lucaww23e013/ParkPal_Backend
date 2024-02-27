package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.VideoDto;
import at.technikum.parkpalbackend.model.Video;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {
    public UserService userService;

    public VideoDto toDto(Video video){
        return VideoDto.builder()
                .videoId(video.getVideoId())
                .userId(video.getUser().getUserId())
                .build();
    }

    public Video toEntity(VideoDto videoDto) {
        return Video.builder()
                .videoId(videoDto.getVideoId())
                .user(userService.findByUserId(videoDto.getUserId()))
                .build();
    }
}
