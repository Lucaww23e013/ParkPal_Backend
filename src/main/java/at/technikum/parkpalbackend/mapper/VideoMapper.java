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

    public VideoDto toDto(Video video) {
        if (video == null) {
            throw new IllegalArgumentException("Video entity or its fields cannot be null");
        }

        return VideoDto.builder()
                .id(video.getId())
                .userId(video.getUser().getId())
                .uploadDate(video.getUploadDate())
                .build();
    }

    public Video toEntity(VideoDto videoDto) {
        if (videoDto == null) {
            throw new IllegalArgumentException("VideoDTO or its fields cannot be null");
        }

        return Video.builder()
                .id(videoDto.getId())
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
