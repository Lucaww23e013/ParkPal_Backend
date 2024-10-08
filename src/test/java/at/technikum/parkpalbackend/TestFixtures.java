package at.technikum.parkpalbackend;

import at.technikum.parkpalbackend.dto.CountryDto;
import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.dto.eventtagdtos.CreateEventTagDto;
import at.technikum.parkpalbackend.dto.eventtagdtos.EventTagDto;
import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginUserDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.model.*;
import at.technikum.parkpalbackend.model.enums.Gender;
import at.technikum.parkpalbackend.model.enums.Role;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


public class TestFixtures {

    public static Country austria = Country.builder().id("c07cd7cb-ce44-4709-a9b6-9d8d2d568263").name("Austria").iso2Code("AT").build();
    public static Country germany = Country.builder().name("Germany").iso2Code("AT").build();

    public static CountryDto austriaDTO = CountryDto.builder().name("AustriaDTO").iso2Code("ATDTO").build();
    public static Address parkAddress = wien1010Address("mariahilfe Str.", 5);
    public static User adminUser = createUser("osama235", "sw@gmail.com", "Osama", "Mac", Role.ADMIN, Gender.MALE, "Mr.");

    public static LoginUserDto adminLoginUserDto = createLoginUserDto();


    public static CreateUserDto adminCreateUserDto = createCreateUserDTO("osama235", "sw@gmail.com", "Osama", "Mac", Role.ADMIN, Gender.MALE, "Mr.");
    public static UserDto adminUserDto = createUserDto("osama235", "sw@gmail.com", "Osama", "Mac", Role.ADMIN, Gender.MALE, "Mr.");
    public static User normalUser = createUser("r221", "raul@gmail.com", "Raul", "Gonzo", Role.USER, Gender.MALE, "Mr.");
    public static Park parkAwesome = createParkWithOutEvents("Awesome Park");

    public static Park parkWithEvents = createParkWithEvents("parkWithEvents");

    public static Park alternateParkWithEvents = createParkWithEvents("alternateParkWithEvents");

    public static Park parkLuca = createParkWithOutEvents("Park only For Lucas");

    public static ParkDto testParkDto = createTestParkDto("testParkDto");

    public static CreateParkDto testCreateParkDto = createCreateParkDto("testCreateParkDto");

   /* public static Media testMedia = createMedia();*/
   /* public static List<Media> mediaList = createMediaList();*/

    /*public static List<Event> eventList = createEventList();*/
    public static List<User> userList = createUserList();
    // Events
    public static Event grilling = createEvent("grilling Biggest Steak Beef");
    public static Event pingPongGame = createEvent("pingPong Game with 4 players");
    public static Event chessMaster = createEvent("Chess Master only for the best players");
    public static Event pickNickWithYourFamily = createEvent("Pick Nick With Your Family");
    // EventTags
    public static EventTag familyEventTag = createEventTag("Family", grilling, pickNickWithYourFamily);
    public static EventTag gamesEventTag = createEventTag("Games", chessMaster, chessMaster);

    public static EventDto testEventDto = createEventDto();
    public static EventDto secondTestEventDto = createEventDto();
    public static EventTagDto testEventTagDto = createEventTagDto();
    public static CreateEventTagDto testCreateEventTagDto = createEventTagDtoMusicWithNoEvents();

    @NotNull
    private static CreateEventTagDto createEventTagDtoMusicWithNoEvents() {
        return new CreateEventTagDto("music", Collections.emptySet());
    }

    public static Set<EventDto> eventDtoSet = createEventDtoSet();


    public static CreateEventDto testCreateEventDto = createCreateEventDto(normalUser, "title1", "a description", parkWithEvents);

    public static CreateEventDto createCreateEventDto (User creator, String title, String description, Park park) {
        CreateEventDto createEventDto = CreateEventDto.builder()
                .creatorUserId(creator.getId())
                .title(title)
                .description(description)
                .startTS(LocalDateTime.now())
                .endTS(LocalDateTime.now().plusHours(1))
                .parkId(park.getId())
                .build();
        return createEventDto;
    }

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

    private static List<User> createUserList() {
        List<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(normalUser);
        joinedUsers.add(adminUser);
        return joinedUsers;
    }


    private static List<Event> createEventList() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(createEvent("EventTitle"));
        eventList.add(createEvent("EventTitle"));
        return eventList;
    }


    private static List<EventDto> createEventDtoList() {
        List<EventDto> eventDtoList = new ArrayList<>();
        eventDtoList.add(createEventDto());
        eventDtoList.add(createEventDto());
        return eventDtoList;
    }

    private static Event createEvent(String title) {
        Event event = Event.builder()
                .title(title)
                .description("Runaway Park")
                .startTS(LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.MINUTES))
                .endTS( LocalDateTime.now().plusHours(2).truncatedTo(ChronoUnit.MINUTES))
                .park(parkAwesome)
                .creator(adminUser)
                .joinedUsers(createUserList())
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
                .build();
    }

    private static ParkDto createTestParkDto(String parkDtoName) {
        return ParkDto.builder()
                .name(parkDtoName)
                .description("ParkDTO Test")
                .address(parkAddress)
                .eventDtos(createEventDtoList())
                .build();
    }
    // TODO Check me
    private static CreateParkDto createCreateParkDto (String createParkDto) {
        return CreateParkDto.builder()
                .name(createParkDto)
                .description("CreateParkDTO Test")
                .address(parkAddress)
                .build();
    }
    // TODO Check me
    private static Park createParkWithEvents(String parkName) {
        return Park.builder()
                .name(parkName)
                .description("Park for Everybody")
                .address(parkAddress)
                .events(createEventList())
                .build();
    }

  /* private static Media createMedia() {
        return Media.builder()
                .user(normalUser)
                .build();
    }*/

    private static UserDto createUserDto(String userName, String email, String firstName, String lastName, Role role, Gender gender, String salutation) {
        return UserDto.builder()
                .id(UUID.randomUUID().toString())
                .salutation(salutation)
                .gender(gender)
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password("eyJhbGciOiJIUzI1NiIsInR5cCI32!")
                .countryId(austria.getId())
                .role(Role.ADMIN)
                .joinedEvents(createEventList())
                .build();
    }

    private static CreateUserDto createCreateUserDTO(String userName, String email, String firstName, String lastName, Role role, Gender gender, String salutation) {
        return CreateUserDto.builder()
                .id(UUID.randomUUID().toString())
                .salutation(salutation)
                .gender(gender)
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password("eyJhbGciOiJIUzI1NiIsInR5cCI32!")
                .countryId(austria.getId())
                .role(Role.ADMIN)
                .build();
    }

    private static LoginUserDto createLoginUserDto() {
        return LoginUserDto.builder()
                .email(UUID.randomUUID().toString())
                .build();

    }

    private static User createUser(String userName, String email, String firstName, String lastName, Role role, Gender gender, String salutation) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .salutation(salutation)
                .gender(gender)
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password("eyJhbGciOiJIUzI1NiIsInR5cCI32!")
                .country(austria)
                .role(Role.ADMIN)
                .joinedEvents(createEventList())
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

    private static EventTagDto createEventTagDto() {


        return EventTagDto.builder()
                .eventIds(createEventDtoSet().stream()
                        .map(EventDto::getTitle).collect(Collectors.toSet()))
                .name("Test")
                .build();
    }

    // TODO Check me
    private static EventDto createEventDto() {
        return EventDto.builder()
                .description("Test")
                .creatorName(TestFixtures.adminUser.getUserName())
                .creatorUserId(TestFixtures.adminUser.getId())
                .startTS(LocalDateTime.now())
                .endTS(LocalDateTime.now())
                .parkId(TestFixtures.parkWithEvents.getId())
                .build();
    }

    private static Set<EventDto> createEventDtoSet() {
        Set<EventDto> eventDtoSet = new HashSet<>();
        eventDtoSet.add(testEventDto);
        eventDtoSet.add(secondTestEventDto);
        return eventDtoSet;
    }

    private static Set<EventTag> createEventTagSet() {
        Set<EventTag> eventTagSet = new HashSet<>();
        eventTagSet.add(familyEventTag);
        eventTagSet.add(gamesEventTag);
        return eventTagSet;
    }

    private static Set<Event> createEventSet() {
        Set<Event> eventSet = new HashSet<>();
        eventSet.add(grilling);
        eventSet.add(chessMaster);
        return eventSet;
    }


    public static Set<EventTag> testEventTagSet = createEventTagSet();

    public static Set<Event> testEventSet = createEventSet();
}
