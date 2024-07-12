package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.FileDto;
import at.technikum.parkpalbackend.mapper.FileMapper;
import at.technikum.parkpalbackend.model.*;
import at.technikum.parkpalbackend.service.FileService;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pictures")
@CrossOrigin
public class FileController {
    private final FileService fileService;

    private final UserService userService;

    private final FileMapper fileMapper;

    public FileController(FileService fileService, UserService userService,
                          FileMapper fileMapper) {
        this.fileService = fileService;
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    @PostMapping
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.CREATED)
    public FileDto createPicture(@RequestBody @Valid FileDto fileDto){
        File createdFile = fileMapper.toEntity(fileDto);
        createdFile = fileService.save(createdFile);
        return fileMapper.toDto(createdFile);
    }
    @GetMapping
    public List<FileDto> getAllPictures() {
        List<File> files = fileService.findAllPictures();
        return files.stream().map(picture -> fileMapper.toDto(picture)).toList();

    }

    @GetMapping("/{pictureId}")
    public FileDto getPictureByPictureId(@PathVariable @Valid String pictureId){
        File file = fileService.findPictureByPictureId(pictureId);
        return fileMapper.toDto(file);
    }

    @GetMapping("/user/{userId}")
    public List<FileDto> getPictureByUserId(@PathVariable @Valid String userId){
        User user = userService.findByUserId(userId);
        List<File> selectedFiles = fileService.findPicturesByUser(user);
        return selectedFiles.stream().map(picture -> fileMapper.toDto(picture)).toList();
    }
    @PatchMapping("/{pictureId}")
    public FileDto updatePicture(@PathVariable String pictureId,
                                 @RequestBody @Valid FileDto updatedFileDto){
        File updatedFile = fileMapper.toEntity(updatedFileDto);
        updatedFile = fileService.updatePicture(pictureId, updatedFile);
        return fileMapper.toDto(updatedFile);
    }
    @DeleteMapping("/{pictureId}")
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.OK)
    public FileDto deletePictureByPictureId(@PathVariable @Valid String pictureId){
        fileService.deletePictureByPictureId(pictureId);
        return null;
    }
}
