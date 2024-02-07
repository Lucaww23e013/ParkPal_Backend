package at.technikum.parkpalbackend.controller;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
@CrossOrigin

//@AllArgsConstructor
@NoArgsConstructor
public class MediaController {

//    @GetMapping
//    public List<MediaDto> getAllMedia() {
//        List<MediaDto> media = new ArrayList<>();
//        List<EventDto> joinedEvents = new ArrayList<>();
//        LocalDateTime toTime = LocalDateTime.of(2024, 12, 12,12,12);
//        joinedEvents.add(new EventDto("111", "event of all Events", "Chess Event", LocalDateTime.now(), toTime, media, ))
//        Country Austria = new Country("111-222-333","Austria",  "2345");
//        User user = new User("111", "mr", "username", "firstname",
//                "lastname", "email@sample.com", "12345", "authToken",
//                Austria, false, );
//        media.add(new MediaDto("1",user ,MediaCategory.PICTURE ));
//        media.add(new MediaDto("2",user, MediaCategory.VIDEO));
//        return media;
//    } // WIP add more Things

}
