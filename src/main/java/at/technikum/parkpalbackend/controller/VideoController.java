package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.VideoDto;
import at.technikum.parkpalbackend.mapper.VideoMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.Video;
import at.technikum.parkpalbackend.service.UserService;
import at.technikum.parkpalbackend.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/videos")
@CrossOrigin
public class VideoController {
    private final UploadController uploadController;
    private final VideoService videoService;

    private final UserService userService;

    private final VideoMapper videoMapper;

    public VideoController(UploadController uploadController, VideoService videoService,
                           UserService userService, VideoMapper videoMapper) {
        this.uploadController = uploadController;
        this.videoService = videoService;
        this.userService = userService;
        this.videoMapper = videoMapper;
    }

    @GetMapping("/videos")
    public List<VideoDto> getAllPicture() {
        List<Video> videos = videoService.findAllVideos();
        return videos.stream().map(video -> videoMapper.toDto(video)).toList();

    }

    @GetMapping("/videos/{videoId}")
    public VideoDto getVideoByVideoId(@PathVariable @Valid String videoId){
        Video video = videoService.findVideoByVideoId(videoId);
        return videoMapper.toDto(video);
    }

    @GetMapping("/videos/{userId}")
    public List<VideoDto> getVideoByUserId(@PathVariable @Valid String userId){
        User user = userService.findByUserId(userId);
        List<Video> selectedVideos = videoService.findVideosByUser(user);
        return selectedVideos.stream().map(video -> videoMapper.toDto(video)).toList();
    }

    /*@PostMapping("/videos/create")
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.CREATED)
    public VideoDto createVideo(@RequestBody @Valid VideoDto videoDto){
        Video createdVideo = videoMapper.toEntity(videoDto);
        createdVideo = videoService.save(createdVideo);
        return videoMapper.toDto(createdVideo);
    }*/

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public VideoDto createVideo(@RequestParam("file") MultipartFile file,
                                @RequestBody @Valid VideoDto videoDto) throws IOException {
        ResponseEntity<String> uploadResponse = uploadController.fileUpload(file);
        if (uploadResponse.getStatusCode().is2xxSuccessful()) {
            Video createdVideo = videoMapper.toEntity(videoDto);
            createdVideo = videoService.save(createdVideo);
            return videoMapper.toDto(createdVideo);
        } else {
            throw new RuntimeException("File upload failed");
        }
    }
    @PatchMapping("/videos/{videoId}")
    public VideoDto updateVideo(@PathVariable String videoId,
                                @RequestBody VideoDto updatedVideoDto){
        Video updatedVideo = videoMapper.toEntity(updatedVideoDto);
        updatedVideo = videoService.updateVideo(videoId, updatedVideo);
        return videoMapper.toDto(updatedVideo);
    }
    @DeleteMapping("/videos/{videoId}")
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.OK)
    public VideoDto deleteVideoByVideoId(@PathVariable @Valid String videoId){
        videoService.deleteVideoByVideoId(videoId);
        return null;
    }
}
