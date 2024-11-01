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
import at.technikum.parkpalbackend.model.enums.FileType;
import at.technikum.parkpalbackend.model.enums.Gender;
import at.technikum.parkpalbackend.model.enums.Role;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


public class TestFixtures {

    public static Country austria = Country.builder().name("Austria").iso2Code("AT").build();
    public static Country germany = Country.builder().name("Germany").iso2Code("DE").build();

    public static CountryDto austriaDTO = CountryDto.builder().name("AustriaDTO").iso2Code("ATDTO").build();
    public static Address parkAddress = wien1010Address("mariahilfe Str.", 5);
    public static Address alternateParkAddress = berlinAddress("Haizingerstr", 5);
    public static User adminUser = createUser("osama235", "sw436436@gmail.com", "Osama", "Mac", Role.ADMIN, Gender.MALE, "Mr.");
    public static User adminUser2 = createUser("osama1", "sw24@gmail.com", "Osama", "Mac", Role.ADMIN, Gender.MALE, "Mr.");
    public static User simpleUser = createSimpleUser("luca ", "luca1234@gmail.com", "Luca", "Mac", Role.ADMIN, Gender.MALE, "Mr.");
    public static User simpleUser2 = createSimpleUser("luca2 ", "luca123w222224@gmail.com", "Luca", "Mac", Role.ADMIN, Gender.MALE, "Mr.");
    public static LoginUserDto adminLoginUserDto = createLoginUserDto();

    public static CreateUserDto adminCreateUserDto = createCreateUserDTO("osama235", "sw@gmail.com", "Osama", "Mac", Role.ADMIN, Gender.MALE, "Mr.");
    public static UserDto adminUserDto = createUserDto("osama235", "sw354364364@gmail.com", "Osama", "Mac", Role.ADMIN, Gender.MALE, "Mr.");
    public static User normalUser = createUser("r221", "raul@gmail.com", "Raul", "Gonzo", Role.USER, Gender.MALE, "Mr.");
    public static User newUser = createUser("r22112121", "Princeraul@gmail.com", "Raul", "Gonzo", Role.USER, Gender.MALE, "Mr.");
    public static User normalAlternateUser = createAlternateUser("p22", "paul@gmail.com", "Raul", "Gonzo", Role.USER, Gender.MALE, "Mr.");
    public static Park parkAwesome = parkWithOutEvents("Awesome Park");
    public static ParkDto parkDtoAwesome = parkDtoWithOutEvents("Awesome Park Dto");
    public static Park parkCool = alternateParkWithOutEvents("Cool Park");
    public static Park alternatePark = alternateParkWithOutEvents("Existing Park Name");
    public static ParkDto parkCoolDto = parkDtoWithOutEvents("Cool Park");
    public static ParkDto parkDtoWithMedia = parkDtoWithMedia("Cool Park");
    public static Park parkWithMedia = parkWithMedia("Media Park");
    public static CreateParkDto CreateParkCoolDto = CreateParkDtoWithOutEvents("Cool Park");
    public static CreateParkDto CreateExpectedParkCoolDto = CreateParkDtoWithOutEvents("Cool Park");

    public static Park parkWithEvents = createParkWithEvents("parkWithEvents");

    public static Park parkWithEventsAndMedia = createParkWithEventsAndMedia("parkWithEventsAndMedia");
    public static ParkDto parkDtoWithEventsAndMediaEmpty = parkDtoWithEventsAndMedia("parkDtoWithEventsAndMedia");

    public static Park alternateParkWithEvents = createParkWithEvents("alternateParkWithEvents");

    public static Park parkLuca = parkWithOutEvents("Park only For Lucas");
    // TODO Check me
    /*
    public static ParkDto testParkDto = createTestParkDto("testParkDto");
    */

    /* public static Media testMedia = createMedia();*/
    /* public static List<Media> mediaList = createMediaList();*/

    /*public static List<Event> eventList = createEventList();*/
    public static List<User> userList = createUserList();
    public static List<User> alternateUserList = createUserList();
    // Events
    public static Event grilling = createEvent("grilling Biggest Steak Beef");
    public static Event pingPongGame = createEvent("pingPong Game with 4 players");
    public static Event chessMaster = createEvent("Chess Master only for the best players");
    public static Event pickNickWithYourFamily = createEvent("Pick Nick With Your Family");
    // EventTags
    public static EventTag familyEventTag = createEventTag("Family", grilling, pickNickWithYourFamily);
    public static EventTag gamesEventTag = createEventTag("Games", chessMaster, chessMaster);

    public static EventDto testEventDto = createEventDto("osama");
    public static EventDto eventDtoWithoutMedia = createEventDtoWithoutMedia("osamaOsama");
    public static EventTagDto testEventTagDto = createEventTagDto();
    public static CreateEventTagDto testCreateEventTagDto = createEventTagDtoMusicWithNoEvents();
    public static List<File> fileList = createMediaList();
    public static List<String> fileIdList = createMediaIdList();

    public static File file = createTestFile("file", adminUser);
    public static File file2 = createTestFile("file2", normalUser);

    @NotNull
    private static CreateEventTagDto createEventTagDtoMusicWithNoEvents() {
        return new CreateEventTagDto("music", Collections.emptySet());
    }

    public static Set<EventDto> eventDtoSet = createEventDtoSet();

    public static CreateEventDto testCreateEventDto = createCreateEventDto(normalUser, "title1", "a description", parkWithEvents);
    public static Event testEvent = createEvent(normalUser, "title1", "a description", parkWithEvents);


    public static Event createEvent(User creator, String title, String description, Park park) {
        Event event = Event.builder().version(0L)
                .creator(creator)
                .title(title)
                .description(description)
                .startTS(LocalDateTime.now())
                .endTS(LocalDateTime.now().plusHours(1))
                .park(park)
                .tags(new HashSet<>(Arrays.asList(familyEventTag, gamesEventTag)))
                .media(new ArrayList<>(Arrays.asList(file, file2)))
                .build();
        return event;
    }

    public static CreateEventDto createCreateEventDto(User creator, String title, String description, Park park) {
        return CreateEventDto.builder()
                .creatorUserId(creator.getId())
                .title(title)
                .description(description)
                .startTS(LocalDateTime.now())
                .endTS(LocalDateTime.now().plusHours(1))
                .parkId(park.getId())
                .eventTagsIds(new HashSet<>(Arrays.asList(familyEventTag.getId(), gamesEventTag.getId())))
                .mediaFileExternalIds(new ArrayList<>(Arrays.asList(familyEventTag.getId(), gamesEventTag.getId())))
                .build();
    }

    private static List<EventTag> createEventTagListForAnEvent(Event event) {
        List<EventTag> eventTags = new ArrayList<>();
        eventTags.add(createEventTag("Family", event));
        eventTags.add(createEventTag("Football", event));
        return eventTags;
    }


    private static EventTag createEventTag(String eventTagName, Event... events) {
        Set<Event> eventSet = new HashSet<>(Arrays.asList(events));
        return EventTag.builder().version(0L)
                .name(eventTagName)
                .events(eventSet)
                .build();
    }

    private static List<User> createAlternateUserList() {
        List<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(normalUser);
        return joinedUsers;
    }

    private static List<User> createUserList() {
        List<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(normalUser);
        joinedUsers.add(normalAlternateUser);
        return joinedUsers;
    }

    private static List<Event> createEventList() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(createEvent("EventTitle"));
        eventList.add(createEvent("EventTitle"));
        return eventList;
    }

    private static List<String> createEventIdList() {
        List<String> eventIdList = new ArrayList<>();
        eventIdList.add(UUID.randomUUID().toString());  // Simulate event IDs as UUID strings
        eventIdList.add(UUID.randomUUID().toString());
        return eventIdList;
    }

    public static List<String> createMediaIdList() {
        List<String> externalIds = new ArrayList<>();
        externalIds.add(UUID.randomUUID().toString());  // You should implement this method to create test File objects
        externalIds.add(UUID.randomUUID().toString());
        return externalIds;
    }

    public static List<File> createMediaList() {
        List<File> mediaList = new ArrayList<>();
        mediaList.add(file);  // You should implement this method to create test File objects
        mediaList.add(file2);
        return mediaList;
    }

    public static File createTestFile(String fileName, User username) {
        return File.builder().version(0L)
                .id(UUID.randomUUID().toString())  // Assign a unique ID
                .filename(fileName)
                .user(username)  // Assign the admin user as the creator
                .externalId(UUID.randomUUID().toString())  // Assign a unique external ID
                .build();
    }

    private static Event createEvent(String title) {
        Event event = Event.builder().version(0L)
                .title(title)
                .description("Runaway Park")
                .startTS(LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.MINUTES))
                .endTS(LocalDateTime.now().plusHours(2).truncatedTo(ChronoUnit.MINUTES))
                .park(parkAwesome)
                .creator(simpleUser)
                .joinedUsers(createUserList())
                .build();
        List<EventTag> eventTags = createEventTagListForAnEvent(event);
        event.addEventTags(eventTags.toArray(EventTag[]::new));
        return event;
    }

    public static CreateParkDto CreateParkDtoWithOutEvents(String parkDtoName) {
        return CreateParkDto.builder()
                .name(parkDtoName)
                .description("Updated Description")
                .address(alternateParkAddress)
                .mediaFileExternalIds(new ArrayList<>())
                .build();
    }

    public static CreateParkDto ExpectedCreateParkDto(String parkDtoName) {
        return CreateParkDto.builder()
                .name(parkDtoName)
                .description("Updated Description")
                .address(alternateParkAddress)
                .mediaFileExternalIds(new ArrayList<>())
                .build();
    }

    public static ParkDto parkDtoWithMedia(String parkDtoName) {
        return ParkDto.builder()
                .name(parkDtoName)
                .description("Updated Description")
                .address(alternateParkAddress)
                .mediaFileExternalIds(fileIdList)
                .build();
    }

    private static Park alternateParkWithOutEvents(String parkName) {
        return Park.builder()
                .version(0L)
                .name(parkName)
                .description("Updated Description")
                .address(alternateParkAddress)
                .media(new ArrayList<>())
                .build();
    }

    private static Park parkWithMedia(String parkName) {
        return Park.builder()
                .version(0L)
                .id(UUID.randomUUID().toString())
                .name(parkName)
                .description("Updated Description")
                .address(alternateParkAddress)
                .media(createMediaList())
                .build();
    }

    private static Park parkWithOutEvents(String parkName) {
        return Park.builder()
                .version(0L)
                .name(parkName)
                .description("Park for Everybody")
                .address(parkAddress)
                .build();
    }

    private static ParkDto parkDtoWithOutEvents(String parkDtoName) {
        return ParkDto.builder()
                .name(parkDtoName)
                .description("ParkDTO Test")
                .address(parkAddress)
                .build();
    }

    private static ParkDto createParkDtoWithEvents(String parkDtoName) {
        return ParkDto.builder()
                .name(parkDtoName)
                .description("ParkDTO Test")
                .address(parkAddress)
                .eventIds(createEventIdList())
                .build();
    }

    private static CreateParkDto createCreateParkDto(String createParkDto) {
        return CreateParkDto.builder()
                .name(createParkDto)
                .description("CreateParkDTO Test")
                .address(parkAddress)
                .build();
    }

    private static Park createParkWithEvents(String parkName) {
        return Park.builder()
                .version(0L)
                .name(parkName)
                .description("Park for Everybody")
                .address(alternateParkAddress)
                .events(createEventList())
                .build();
    }

    private static Park createAlternateParkWithEventsAndMedia(String parkName) {
        return Park.builder()
                .version(0L)
                .name(parkName)
                .description("Park for Everybody")
                .address(parkAddress)
                .events(createEventList())  // Assuming this method returns List<Event>
                .media(createMediaList())  // New method to create media list
                .build();

    }

    private static Park createParkWithEventsAndMedia(String parkName) {
        return Park.builder()
                .version(0L)
                .name(parkName)
                .description("Park for Everybody")
                .address(parkAddress)
                .events(createEventList())  // Assuming this method returns List<Event>
                .media(createMediaList())  // New method to create media list
                .build();
    }

    private static ParkDto parkDtoWithEventsAndMedia(String parkDtoName) {
        // Mock event IDs and file external IDs
        List<String> mockEventIds = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        List<String> mockFileExternalIds = List.of("file-ext-1", "file-ext-2");

        return ParkDto.builder()
                .name(parkDtoName)
                .description("Update Park")
                .address(alternateParkAddress)
                .eventIds(new ArrayList<>())
                .mediaFileExternalIds(new ArrayList<>())
                .build();
    }


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
                .joinedEventsIds(createEventList().stream()
                        .map(Event::getId).collect(Collectors.toList()))
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

    private static User createSimpleUser(String userName, String email, String firstName, String lastName, Role role, Gender gender, String salutation) {
        return User.builder()
                .version(0L)
                .salutation(salutation)
                .gender(gender)
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password("eyJhbGciOiJIUzI1NiIsInR5cCI32!")
                .role(Role.ADMIN)
                .build();
    }

    private static User createUser(String userName, String email, String firstName, String lastName, Role role, Gender gender, String salutation) {
        return User.builder()
                .version(0L)
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

    private static User createAlternateUser(String userName, String email, String firstName, String lastName, Role role, Gender gender, String salutation) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .version(0L)
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

    public static Address berlinAddress(String streetName, Integer number) {
        return Address.builder()
                .streetNumber(streetName)
                .zipCode("10115")
                .city("Berlin")
                .country(germany)
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
    private static EventDto createEventDto(String creatorName) {
        return EventDto.builder()
                .title("Title")
                .description("Test")
                .creatorName(creatorName)
                .creatorUserId(UUID.randomUUID().toString())
                .startTS(LocalDateTime.now().plusHours(1))
                .endTS(LocalDateTime.now().plusHours(2))
                .parkId(UUID.randomUUID().toString())
                .mediaFileExternalIds(fileIdList)
                .build();
    }

    // TODO Check me
    private static EventDto createEventDtoWithoutMedia(String creatorName) {
        return EventDto.builder()
                .title("Title")
                .description("Test")
                .creatorName(creatorName)
                .creatorUserId(UUID.randomUUID().toString())
                .startTS(LocalDateTime.now().plusHours(1))
                .endTS(LocalDateTime.now().plusHours(2))
                .parkId(UUID.randomUUID().toString())
                .build();
    }

    private static Set<EventDto> createEventDtoSet() {
        Set<EventDto> eventDtoSet = new HashSet<>();
        eventDtoSet.add(testEventDto);
        eventDtoSet.add(eventDtoWithoutMedia);
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


    public static Park parkWithFiles() {
        Park park = Park.builder().version(0L).build();
        park.setId(UUID.randomUUID().toString());
        park.setName("Test Park with Files");
        park.setDescription("This is a park with files.");
        park.setAddress(parkAddress);

        // Create associated files for the park
        filesWithPark(park);

        return park;
    }

    private static void filesWithPark(Park park) {
        List<File> files = new ArrayList<>();
        File file1 = File.builder()
                .externalId(UUID.randomUUID().toString())
                .version(0L)
                .filename("file1.jpg")
                .fileType(FileType.PHOTO)
                .park(park)
                .assigned(true)
                .build();
        File file2 = File.builder()
                .externalId(UUID.randomUUID().toString())
                .version(0L)
                .filename("file2.jpg")
                .fileType(FileType.PHOTO)
                .park(park)
                .assigned(true)
                .build();
        files.add(file1);
        files.add(file2);
        park.setMedia(files);  // Assuming media refers to the list of files
    }

    public static Park parkWithUpdatedFiles() {
        Park park = Park.builder().version(0L).build();
        park.setId(UUID.randomUUID().toString());
        park.setName("Updated Park with Files");
        park.setDescription("This is an updated park with new files.");
        park.setAddress(parkAddress);

        // Create new associated files for the updated park
        filesWithPark(park);

        return park;
    }
}
