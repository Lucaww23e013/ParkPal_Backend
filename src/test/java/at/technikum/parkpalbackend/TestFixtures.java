package at.technikum.parkpalbackend;

import at.technikum.parkpalbackend.dto.PictureDto;
import at.technikum.parkpalbackend.model.*;
import at.technikum.parkpalbackend.model.enums.Role;
import at.technikum.parkpalbackend.model.enums.Salutation;

import java.time.LocalDateTime;
import java.util.*;


public class TestFixtures {

    public static Country austria = Country.builder().name("Austria").iso2Code("AT").build();
    public static Country germany = Country.builder().name("Germany").iso2Code("AT").build();
    public static Address parkAddress = wien1010Address("mariahilfe Str.", 5);
    public static User adminUser = createUser("osama235", "sw@gmail.com", "Osama", "Mac", Role.ADMIN);
    public static User normalUser = createUser("r221", "raul@gmail.com", "Raul", "Gonzo", Role.USER);
    public static Park parkAwesome = createParkWithOutEvents("Awesome Park");

    public static Park parkWithEvents = createParkWithEvents("parkWithEvents");

    public static Park alternateParkWithEvents = createParkWithEvents("alternateParkWithEvents");

    public static Park parkLuca = createParkWithOutEvents("Park only For Lucas");

   /* public static Media testMedia = createMedia();*/
   /* public static List<Media> mediaList = createMediaList();*/

    /*public static List<Event> eventList = createEventList();*/
    public static List<User> userList = createUserlist();
    // Events
    public static Event grilling = createEvent("grilling Biggest Steak Beef");
    public static Event pingPongGame = createEvent("pingPong Game with 4 players");
    public static Event chessMaster = createEvent("Chess Master only for the best players");
    public static Event pickNickWithYourFamily = createEvent("Pick Nick With Your Family");
    // EventTags
    public static EventTag familyEventTag = createEventTag("Family", grilling, pickNickWithYourFamily);
    public static EventTag gamesEventTag = createEventTag("Games", chessMaster, chessMaster);

    public static byte[] testFile = new byte[100];
    public static Picture testPicture = Picture.builder().id(UUID.randomUUID().toString())
            .user(normalUser)
            .uploadDate(LocalDateTime.now())
            .file(testFile).build();

    public static PictureDto testPictureDto = PictureDto.builder().id(UUID.randomUUID().toString())
            .userId(normalUser.getId())
            .uploadDate(LocalDateTime.now())
            .build();

    public static Picture alternateTestPicture = Picture.builder().id(UUID.randomUUID().toString())
            .user(normalUser)
            .uploadDate(LocalDateTime.now())
            .file(testFile).build();

    public static byte[] testVideoFile = new byte[100];
    public static Video testVideo = Video.builder().id(UUID.randomUUID().toString())
            .user(normalUser)
            .uploadDate(LocalDateTime.now())
            .file(testVideoFile).build();

    public static Video alternateTestVideo = Video.builder().id(UUID.randomUUID().toString())
            .user(normalUser)
            .uploadDate(LocalDateTime.now())
            .file(testVideoFile).build();

    private static List<EventTag> createEventTagListForAnEvent(Event event) {
        List<EventTag> eventTags = new ArrayList<>();
        eventTags.add(createEventTag("Family", event));
        eventTags.add(createEventTag("Football", event));
        return eventTags;
    }


    private static EventTag createEventTag(String eventTagName, Event ...events) {
        Set<Event> eventSet = new HashSet<>();
        for (Event event : events) {
            eventSet.add(event);
        }
        return EventTag.builder()
                .name(eventTagName)
                .events(eventSet)
                .build();
    }

    private static List<User> createUserlist() {
        List<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(normalUser);
        joinedUsers.add(adminUser);
        return joinedUsers;
    }


    public static List<Picture> pictureList() {
        List<Picture> pictureList = new ArrayList<>();
        pictureList.add(testPicture);
        pictureList.add(alternateTestPicture);
        return pictureList;
    }
    public static List<Video> videoList() {
        List<Video> videoList = new ArrayList<>();
        videoList.add(testVideo);
        videoList.add(alternateTestVideo);
        return videoList;
    }


   /* private static List<Media> createMediaList() {
        List<Media> mediaList = new ArrayList<>();
        mediaList.add(createMedia());
        mediaList.add(createMedia());
        return mediaList;
    }*/

    private static List<Event> createEventList() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(createEvent("EventTitle"));
        eventList.add(createEvent("EventTitle"));
        return eventList;
    }

    private static Event createEvent(String title) {
        Event event = Event.builder()
                .title(title)
                .description("Runaway Park")
                .startTS(LocalDateTime.now().plusHours(1))
                .endTS( LocalDateTime.now().plusHours(2))
                .park(parkAwesome)
                .creator(adminUser)
                .joinedUsers(createUserlist())
                .build();
        List<EventTag> eventTags = createEventTagListForAnEvent(event);
        event.addEventTags(eventTags.toArray(EventTag[]::new));
        return event;
    }
    private static Park createParkWithOutEvents(String parkName) {
        return Park.builder()
                .name(parkName)
                .description("Park for Everybody")
                .address(parkAddress)
               /* .parkMedia(createMediaList())*/
                .build();
    }
    private static Park createParkWithEvents(String parkName) {
        return Park.builder()
                .name(parkName)
                .description("Park for Everybody")
                .address(parkAddress)
               // .parkMedia(createMediaList())
                .parkEvents(createEventList())
                .build();
    }

  /* private static Media createMedia() {
        return Media.builder()
                .user(normalUser)
                .build();
    }*/

    private static User createUser(String userName, String email, String firstName, String lastName, Role role) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .salutation(Salutation.MALE)
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password("eyJhbGciOiJIUzI1NiIsInR5cCI32!")
                .country(austria)
                .role(Role.ADMIN)
                .build();
    }

    public static Address wien1010Address(String streetName, Integer number) {
        return Address.builder()
                .streetNumber(streetName)
                .zipCode("1010")
                .city("Wien")
                .country(austria)
                .build();
    }
}
