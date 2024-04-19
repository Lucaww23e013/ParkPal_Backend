package at.technikum.parkpalbackend;

import at.technikum.parkpalbackend.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TestFixtures {

    public static Country austria = Country.builder().name("Austria").iso2Code("AT").build();
    public static Country germany = Country.builder().name("Germany").iso2Code("AT").build();
    public static Address parkAddress = wien1010Address("mariahilfe Str.", 5);
    public static User adminUser = createUser("osama235", "sw@gmail.com", "Osama", "Mac", true);
    public static User normalUser = createUser("r221", "raul@gmail.com", "Raul", "Gonzo", false);
    public static Park parkAwesome = createParkWithOutEvents("Awesome Park");

    public static Park parkWithEvents = createParkWithEvents("parkWithEvents");

    public static Park parkLuca = createParkWithOutEvents("Park only For Lucas");

   /* public static Media testMedia = createMedia();*/
   /* public static List<Media> mediaList = createMediaList();*/

    /*public static List<Event> eventList = createEventList();*/
    public static List<User> userList = createUserlist();
    public static Event grilling = createEvent("grilling Biggest Steak Beef");




    private static List<EventTag> createEventTagListForAnEvent(Event event) {
        List<EventTag> eventTags = new ArrayList<>();
        eventTags.add(createEventTag("Family", event));
        eventTags.add(createEventTag("Football", event));
        return eventTags;
    }


    private static EventTag createEventTag(String eventTagName, Event event) {
        return EventTag.builder()
                .name(eventTagName)
                .build();
    }

    private static List<User> createUserlist() {
        List<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(normalUser);
        joinedUsers.add(adminUser);
        return joinedUsers;
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

    private static User createUser(String userName, String email, String firstName, String lastName, boolean isAdmin) {
        return User.builder()
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

    private static Address wien1010Address(String streetName, Integer number) {
        return Address.builder()
                .streetNumber(streetName)
                .zipCode("1010")
                .city("Wien")
                .country(austria)
                .build();
    }
}
