package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.PictureDto;
import at.technikum.parkpalbackend.mapper.PictureMapper;
import at.technikum.parkpalbackend.model.*;
import at.technikum.parkpalbackend.service.PictureService;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/picture")
@CrossOrigin
public class PictureController {
    private final PictureService pictureService;

    private final UserService userService;

    private final PictureMapper pictureMapper;

    public PictureController(PictureService pictureService, UserService userService, PictureMapper pictureMapper) {
        this.pictureService = pictureService;
        this.userService = userService;
        this.pictureMapper = pictureMapper;
    }

    @GetMapping("/pictures")
    public List<PictureDto> getAllPictures() {
        List<Picture> pictures = pictureService.findAllPictures();
        return pictures.stream().map(picture -> pictureMapper.toDto(picture)).toList();

    }

    @GetMapping("/pictures/{pictureId}")
    public PictureDto getPictureByPictureId(@PathVariable @Valid String pictureId){
        Picture picture = pictureService.findPictureByPictureId(pictureId);
        return pictureMapper.toDto(picture);
    }

    @GetMapping("/pictures/{userId}")
    public List<PictureDto> getPictureByUserId(@PathVariable @Valid String userId){
            User user = userService.findByUserId(userId);
            List<Picture> selectedPictures = pictureService.getPictureByUserId(user);
            return selectedPictures.stream().map(picture -> pictureMapper.toDto(picture)).toList();
    }

    @PostMapping("/pictures/create")
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.CREATED)
    public PictureDto createPicture(@RequestBody @Valid PictureDto pictureDto){
        Picture createdPicture = pictureMapper.toEntity(pictureDto);
        createdPicture = pictureService.save(createdPicture);
        return pictureMapper.toDto(createdPicture);
    }
    @PatchMapping("/pictures/{pictureId}")
    public PictureDto updatePicture(@PathVariable String pictureId, @RequestBody PictureDto updatedPictureDto){
        Picture updatedPicture = pictureMapper.toEntity(updatedPictureDto);
        updatedPicture = pictureService.updatePicture(pictureId, updatedPicture);
        return pictureMapper.toDto(updatedPicture);
    }
    @DeleteMapping("/pictures/{pictureId}")
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.OK)
    public PictureDto deletePictureByPictureId(@PathVariable @Valid String pictureId){
        Picture picture = pictureService.deletePictureByPictureId(pictureId);
        return null;
    }



}
