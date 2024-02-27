package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.VideoDto;
import at.technikum.parkpalbackend.mapper.VideoMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.Video;
import at.technikum.parkpalbackend.service.UserService;
import at.technikum.parkpalbackend.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/videos")
@CrossOrigin
public class VideoController {
    private final VideoService videoService;

    private final UserService userService;

    private final VideoMapper videoMapper;

    public VideoController(VideoService videoService, UserService userService,
                           VideoMapper videoMapper) {
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

    @PostMapping("/videos/create")
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.CREATED)
    public VideoDto createVideo(@RequestBody @Valid VideoDto videoDto){
        Video createdVideo = videoMapper.toEntity(videoDto);
        createdVideo = videoService.save(createdVideo);
        return videoMapper.toDto(createdVideo);
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
