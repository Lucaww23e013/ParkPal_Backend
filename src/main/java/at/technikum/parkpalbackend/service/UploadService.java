package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.mapper.PictureMapper;
import at.technikum.parkpalbackend.mapper.VideoMapper;
import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.model.Video;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    private static final List<String> ALLOWED_PICTURE_TYPES = Arrays.asList("jpg", "png", "gif");
    private static final List<String> ALLOWED_VIDEO_TYPES = Arrays.asList("wmv", "mp4");

    private final PictureService pictureService;
    private final VideoService videoService;

    private final PictureMapper pictureMapper;

    private final VideoMapper videoMapper;

    public UploadService(PictureService pictureService, VideoService videoService,
                         PictureMapper pictureMapper, VideoMapper videoMapper) {
        this.pictureService = pictureService;
        this.videoService = videoService;
        this.pictureMapper = pictureMapper;
        this.videoMapper = videoMapper;
    }

    public ResponseEntity<String> processAndSaveFile(MultipartFile file) {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (ALLOWED_PICTURE_TYPES.contains(fileExtension)) {
            Picture picture = pictureMapper.fromMultipartFileToEntity(file);
            picture.setUploadDate(LocalDateTime.now());
            pictureService.save(picture);
            return ResponseEntity.ok("Upload Successfully");

        } else if (ALLOWED_VIDEO_TYPES.contains(fileExtension)) {
            Video video = videoMapper.fromMultipartFileToEntity(file);
            videoService.save(video);
            video.setUploadDate(LocalDateTime.now());
            return ResponseEntity.ok("Upload Successfully");

        } else {
            return ResponseEntity.badRequest().body("Invalid file type. Allowed types: "
                    + getAllowedFileTypes());
        }
    }

    public List<String> getAllowedFileTypes() {
        List<String> allowedFileTypes = new ArrayList<>(ALLOWED_PICTURE_TYPES);
        allowedFileTypes.addAll(ALLOWED_VIDEO_TYPES);
        return allowedFileTypes;
    }
}

