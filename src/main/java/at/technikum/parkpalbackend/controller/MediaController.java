package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.MediaDto;
import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.model.MediaCategory;
import at.technikum.parkpalbackend.model.User;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parks")
@CrossOrigin

//@AllArgsConstructor
@NoArgsConstructor
public class MediaController {

    @GetMapping
    public List<MediaDto> getAllMedia() {
        List<MediaDto> media = new ArrayList<>();
        Country Austria = new Country("Austria",  "2345");
        User user = new User("111", "mr", "username", "firstname",
                "lastname", "email@sample.com", "12345", "authToken",
                Austria);
        media.add(new MediaDto("1",user ,MediaCategory.PICTURE ));
        media.add(new MediaDto("2",user, MediaCategory.VIDEO));
        return media;
    } // WIP add more Things

}
