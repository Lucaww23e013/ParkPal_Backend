package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.MediaDto;
import at.technikum.parkpalbackend.mapper.MediaMapper;
import at.technikum.parkpalbackend.model.*;
import at.technikum.parkpalbackend.service.MediaService;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/media")
@CrossOrigin
public class MediaController {
    private final MediaService mediaService;

    private final UserService userService;

    private final MediaMapper mediaMapper;

    public MediaController(MediaService mediaService, UserService userService, MediaMapper mediaMapper) {
        this.mediaService = mediaService;
        this.userService = userService;
        this.mediaMapper = mediaMapper;
    }

    @GetMapping("/media")
    public List<MediaDto> getAllParks() {
        List<Media> allMedia = mediaService.findAllMedia();
        return allMedia.stream().map(media -> mediaMapper.toDto(media)).toList();

    }

    @GetMapping("/media/{mediaId}")
    public MediaDto getMediaByMediaId(@PathVariable @Valid String mediaId){
        Media media = mediaService.findMediaByMediaId(mediaId);
        return mediaMapper.toDto(media);
    }

    @GetMapping("/media/{userId}")
    public List<MediaDto> getMediaByUserId(@PathVariable @Valid String userId){
            User user = userService.findByUserId(userId);
            List<Media> selectedMedia = mediaService.getMediaByUser(user);
            return selectedMedia.stream().map(media -> mediaMapper.toDto(media)).toList();
    }

    @PostMapping("/media/create")
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.CREATED)
    public MediaDto createMedia(@RequestBody @Valid MediaDto mediaDto){
        Media createdMedia = mediaMapper.toEntity(mediaDto);
        createdMedia = mediaService.save(createdMedia);
        return mediaMapper.toDto(createdMedia);
    }
    @PatchMapping("/media/{mediaId}")
    public MediaDto updateMedia(@PathVariable String mediaId, @RequestBody MediaDto updatedMediaDto){
        Media updatedMedia = mediaMapper.toEntity(updatedMediaDto);
        updatedMedia = mediaService.updateMedia(mediaId, updatedMedia);
        return mediaMapper.toDto(updatedMedia);
    }
    @DeleteMapping("/media/{mediaId}")
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.OK)
    public MediaDto deleteMediaByMediaId(@PathVariable @Valid String mediaId){
        Media media = mediaService.deleteMediaByMediaId(mediaId);
        return null;
    }



}
