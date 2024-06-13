package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.mapper.PictureMapper;
import at.technikum.parkpalbackend.mapper.VideoMapper;
import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.model.Video;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    private static final List<String> ALLOWED_PICTURE_TYPES
            = new ArrayList<>(Arrays.asList("jpg", "jpeg", "png", "gif"));
    private static final List<String> ALLOWED_VIDEO_TYPES
            = new ArrayList<>(Arrays.asList("mp4", "avi", "mov"));


    private final PictureMapper pictureMapper;
    private final VideoMapper videoMapper;
    private final PictureService pictureService;
    private final VideoService videoService;

    public UploadService(PictureMapper pictureMapper, VideoMapper videoMapper,
                         PictureService pictureService, VideoService videoService) {
        this.pictureMapper = pictureMapper;
        this.videoMapper = videoMapper;
        this.pictureService = pictureService;
        this.videoService = videoService;
    }

    public ResponseEntity<String> processAndSaveFile(MultipartFile file) throws IOException {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (fileExtension != null) {
            if (ALLOWED_PICTURE_TYPES.contains(fileExtension.toLowerCase())) {
                Picture picture = pictureMapper.fromMultipartFileToEntity(file);
                picture.setUploadDate(LocalDateTime.now());
                pictureService.save(picture);

                return ResponseEntity.ok("Picture uploaded successfully");

            } else if (ALLOWED_VIDEO_TYPES.contains(fileExtension.toLowerCase())) {
                Video video = videoMapper.fromMultipartFileToEntity(file);
                video.setUploadDate(LocalDateTime.now());
                videoService.save(video);

                return ResponseEntity.ok("Video uploaded successfully");
            }
        }

        return ResponseEntity.badRequest().body("Invalid file type. Allowed picture types: "
                + String.join(", ", ALLOWED_PICTURE_TYPES) +
                ". Allowed video types: " + String.join(", ", ALLOWED_VIDEO_TYPES));
    }
    public byte[] transferToBytes(MultipartFile file) throws IOException {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new IOException("Failed to read bytes from MultipartFile", e);
        }
    }
}
