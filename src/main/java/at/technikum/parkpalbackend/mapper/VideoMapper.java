package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.VideoDto;
import at.technikum.parkpalbackend.model.Video;
import at.technikum.parkpalbackend.service.UploadService;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class VideoMapper {
    public UserService userService;
    public UploadService uploadService;

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
                .uploadDate(videoDto.getUploadDate())
                .build();
    }

    public Video fromMultipartFileToEntity(MultipartFile file) throws IOException {
        return Video.builder().file(uploadService.transferToBytes(file)).build();
        // add logic later according to usage;
        // maybe change Picture to MultipartFileType;
    }
}
