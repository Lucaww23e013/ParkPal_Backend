package at.technikum.parkpalbackend.componentTests.service;

import at.technikum.parkpalbackend.exception.EntityAlreadyExistsException;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.enums.Role;
import at.technikum.parkpalbackend.persistence.UserRepository;
import at.technikum.parkpalbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserServiceComponentTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Start with a clean slate for each test
    }

    @Test
    void testFindAllUsers_EmptyDatabase_ShouldReturnEmptyList() {
        // Arrange: Database is empty

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userService.findAll(),
                "Expected NoSuchElementException for an empty database");

        assertTrue(exception.getMessage().contains("No users found"));
    }

    @Test
    void testFindAllUsers_SingleUser_ShouldReturnOneUser() {
        // Arrange: Add one user to the database
        User user = User.builder()
                .userName("testuser")
                .email("testuser@example.com")
                .password("Test@12345678")
                .build();
        userRepository.save(user);

        // Act
        List<User> users = userService.findAll();

        // Assert
        assertEquals(1, users.size(), "Expected exactly one user in the database");
        assertEquals("testuser", users.get(0).getUserName());
    }

    @Test
    void testFindAllUsers_MultipleUsers_ShouldReturnAllUsers() {
        // Arrange: Add multiple users to the database
        User user1 = User.builder()
                .userName("user1")
                .email("user1@example.com")
                .password("Test@12345678")
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .userName("user2")
                .email("user2@example.com")
                .password("Test@12345678")
                .build();
        userRepository.save(user2);

        // Act
        List<User> users = userService.findAll();

        // Assert
        assertEquals(2, users.size(), "Expected two users in the database");
        assertTrue(users.stream().anyMatch(u -> u.getUserName().equals("user1")), "user1 should be present");
        assertTrue(users.stream().anyMatch(u -> u.getUserName().equals("user2")), "user2 should be present");
    }

    @Test
    void testFindUserById_ExistingId_ShouldReturnUser() {
        // Arrange: Add a user with a known ID to the database
        User savedUser = userRepository.save(User.builder()
                .userName("sampleuser")
                .email("sampleuser@example.com")
                .password("Test@12345678")
                .build());

        // Act: Retrieve the user by its ID
        User retrievedUser = userService.findByUserId(savedUser.getId());

        // Assert: Verify the retrieved user matches the expected data
        assertEquals(savedUser.getId(), retrievedUser.getId(), "Expected user ID to match the saved user's ID");
        assertEquals(savedUser.getUserName(), retrievedUser.getUserName(), "Expected user name to match the saved user's name");
    }

    @Test
    void testFindUserById_NonExistingId_ShouldThrowEntityNotFoundException() {
        // Arrange: Set up a non-existing user ID
        String nonExistingId = "non-existing-id";

        // Act & Assert: Attempt to retrieve a user with the non-existing ID, expecting an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.findByUserId(nonExistingId),
                "Expected EntityNotFoundException for a non-existing user ID");

        assertTrue(exception.getMessage().contains("User with Id " + nonExistingId + " not found"));
    }

    @Test
    void testFindUserById_NullId_ShouldThrowIllegalArgumentException() {
        // Arrange: Define a null ID for retrieval
        String nullId = null;

        // Act & Assert: Attempt to retrieve a user with a null ID, expecting an exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.findByUserId(nullId),
                "Expected IllegalArgumentException for a null user ID");

        assertTrue(exception.getMessage().contains("User Id is not valid"));
    }

    @Test
    void testSaveUser_ValidUser_ShouldSaveAndReturnUser() {
        // Arrange: Create a valid user
        User user = User.builder()
                .userName("newuser")
                .email("newuser@example.com")
                .password("Test@12345678")
                .build();

        // Act: Save the user
        User savedUser = userService.save(user);

        // Assert: Verify the user was saved correctly
        assertNotNull(savedUser.getId(), "Expected saved user to have an ID");
        assertEquals("newuser", savedUser.getUserName(), "Expected user name to match");
    }

    @Test
    void testSaveUser_DuplicateEmail_ShouldThrowEntityAlreadyExistsException() {
        // Arrange: Create and save a user
        User user = User.builder()
                .userName("user1")
                .email("duplicate@example.com")
                .password("Test@12345678")
                .build();
        userRepository.save(user);

        // Act & Assert: Attempt to save another user with the same email, expecting an exception
        User duplicateUser = User.builder()
                .userName("user2")
                .email("duplicate@example.com")
                .password("Test@12345678")
                .build();

        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class,
                () -> userService.save(duplicateUser),
                "Expected EntityAlreadyExistsException for duplicate email");

        assertTrue(exception.getMessage().contains("A user with the email duplicate@example.com already exists."));
    }

    @Test
    void testUpdateUser_ExistingUser_ShouldUpdateAndReturnUser() {
        // Arrange: Create and save a user
        User user = User.builder()
                .userName("existinguser")
                .email("existinguser@example.com")
                .password("Test@12345678")
                .build();
        User savedUser = userRepository.save(user);

        // Act: Update the user's details
        User updatedUser = User.builder()
                .userName("updateduser")
                .email("updateduser@example.com")
                .password("Test@12345678")
                .build();
        User result = userService.update(savedUser.getId(), updatedUser);

        // Assert: Verify the user's details were updated correctly
        assertEquals(savedUser.getId(), result.getId(), "Expected user ID to remain the same");
        assertEquals("updateduser", result.getUserName(), "Expected user name to be updated");
        assertEquals("updateduser@example.com", result.getEmail(), "Expected email to be updated");
    }

    @Test
    void testDeleteUser_ExistingUser_ShouldDeleteUser() {
        // Arrange: Create and save a user
        User user = User.builder()
                .userName("tobedeleted")
                .email("tobedeleted@example.com")
                .password("Test@12345678")
                .build();
        User savedUser = userRepository.save(user);

        // Act: Delete the user
        userService.delete(savedUser.getId());

        // Assert: Verify the user was deleted
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.findByUserId(savedUser.getId()),
                "Expected EntityNotFoundException for deleted user ID");

        assertTrue(exception.getMessage().contains("User with Id " + savedUser.getId() + " not found"));
    }

    @Test
    void testFindByUserEmail_ExistingEmail_ShouldReturnUser() {
        // Arrange: Add a user with a known email to the database
        User savedUser = userRepository.save(User.builder()
                .userName("emailuser")
                .email("emailuser@example.com")
                .password("Test@12345678")
                .build());

        // Act: Retrieve the user by email
        User retrievedUser = userService.findByUserEmail(savedUser.getEmail());

        // Assert: Verify the retrieved user matches the expected data
        assertEquals(savedUser.getEmail(), retrievedUser.getEmail(), "Expected email to match the saved user's email");
        assertEquals(savedUser.getUserName(), retrievedUser.getUserName(), "Expected user name to match the saved user's name");
    }

    @Test
    void testFindByUserEmail_NonExistingEmail_ShouldThrowEntityNotFoundException() {
        // Arrange: Set up a non-existing email
        String nonExistingEmail = "nonexisting@example.com";

        // Act & Assert: Attempt to retrieve a user with the non-existing email, expecting an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.findByUserEmail(nonExistingEmail),
                "Expected EntityNotFoundException for a non-existing email");

        assertTrue(exception.getMessage().contains("Email " + nonExistingEmail + " not found"));
    }

    @Test
    void testFindByUserName_ExistingName_ShouldReturnUser() {
        // Arrange: Add a user with a known username to the database
        User savedUser = userRepository.save(User.builder()
                .userName("nameuser")
                .email("nameuser@example.com")
                .password("Test@12345678")
                .build());

        // Act: Retrieve the user by username
        User retrievedUser = userService.findByUserName(savedUser.getUserName());

        // Assert: Verify the retrieved user matches the expected data
        assertEquals(savedUser.getUserName(), retrievedUser.getUserName(), "Expected username to match the saved user's username");
        assertEquals(savedUser.getEmail(), retrievedUser.getEmail(), "Expected email to match the saved user's email");
    }

    @Test
    void testFindByUserName_NonExistingName_ShouldThrowEntityNotFoundException() {
        // Arrange: Set up a non-existing username
        String nonExistingName = "nonexistinguser";

        // Act & Assert: Attempt to retrieve a user with the non-existing username, expecting an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.findByUserName(nonExistingName),
                "Expected EntityNotFoundException for a non-existing username");

        assertTrue(exception.getMessage().contains("Username " + nonExistingName + " not found"));
    }

    @Test
    void testUpdateUser_NonExistingUser_ShouldThrowEntityNotFoundException() {
        // Arrange: Set up a non-existing user ID
        String nonExistingId = "non-existing-id";
        User updatedUser = User.builder()
                .userName("updateduser")
                .email("updateduser@example.com")
                .password("Test@12345678")
                .build();

        // Act & Assert: Attempt to update a user with the non-existing ID, expecting an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.update(nonExistingId, updatedUser),
                "Expected EntityNotFoundException for a non-existing user ID");

        assertTrue(exception.getMessage().contains("User with Id " + nonExistingId + " not found"));
    }

    @Test
    void testDeleteUser_NonExistingUser_ShouldThrowEntityNotFoundException() {
        // Arrange: Set up a non-existing user ID
        String nonExistingId = "non-existing-id";

        // Act & Assert: Attempt to delete a user with the non-existing ID, expecting an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.delete(nonExistingId),
                "Expected EntityNotFoundException for a non-existing user ID");

        assertTrue(exception.getMessage().contains("User with Id " + nonExistingId + " not found"));
    }

    @Test
    void testFindByUserNameOrEmail_ExistingEmail_ShouldReturnUser() {
        // Arrange: Add a user with a known email to the database
        User savedUser = userRepository.save(User.builder()
                .userName("emailuser")
                .email("emailuser@example.com")
                .password("Test@12345678")
                .build());

        // Act: Retrieve the user by email
        User retrievedUser = userService.findByUserNameOrEmail(savedUser.getEmail());

        // Assert: Verify the retrieved user matches the expected data
        assertEquals(savedUser.getEmail(), retrievedUser.getEmail(), "Expected email to match the saved user's email");
        assertEquals(savedUser.getUserName(), retrievedUser.getUserName(), "Expected user name to match the saved user's name");
    }

    @Test
    void testFindByUserNameOrEmail_ExistingUsername_ShouldReturnUser() {
        // Arrange: Add a user with a known username to the database
        User savedUser = userRepository.save(User.builder()
                .userName("nameuser")
                .email("nameuser@example.com")
                .password("Test@12345678")
                .build());

        // Act: Retrieve the user by username
        User retrievedUser = userService.findByUserNameOrEmail(savedUser.getUserName());

        // Assert: Verify the retrieved user matches the expected data
        assertEquals(savedUser.getUserName(), retrievedUser.getUserName(), "Expected username to match the saved user's username");
        assertEquals(savedUser.getEmail(), retrievedUser.getEmail(), "Expected email to match the saved user's email");
    }

    @Test
    void testFindByUserNameOrEmail_NonExistingInput_ShouldThrowEntityNotFoundException() {
        // Arrange: Set up a non-existing username or email
        String nonExistingInput = "nonexistinguser";

        // Act & Assert: Attempt to retrieve a user with the non-existing input, expecting an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.findByUserNameOrEmail(nonExistingInput),
                "Expected EntityNotFoundException for a non-existing username or email");

        assertTrue(exception.getMessage().contains("Username %s not found".formatted(nonExistingInput)));
    }

    @Test
    void testFindUsersByIds_ExistingIds_ShouldReturnUsers() {
        // Arrange: Add multiple users to the database
        User user1 = userRepository.save(User.builder()
                .userName("user1")
                .email("user1@example.com")
                .password("Test@12345678")
                .build());
        User user2 = userRepository.save(User.builder()
                .userName("user2")
                .email("user2@example.com")
                .password("Test@12345678")
                .build());

        // Act: Retrieve the users by their IDs
        List<User> users = userService.findUsersByIds(List.of(user1.getId(), user2.getId()));

        // Assert: Verify the retrieved users match the expected data
        assertEquals(2, users.size(), "Expected two users in the database");
        assertTrue(users.stream().anyMatch(u -> u.getUserName().equals("user1")), "user1 should be present");
        assertTrue(users.stream().anyMatch(u -> u.getUserName().equals("user2")), "user2 should be present");
    }

    @Test
    void testFindUsersByIds_NonExistingIds_ShouldReturnEmptyList() {
        // Arrange: Set up non-existing user IDs
        List<String> nonExistingIds = List.of("non-existing-id1", "non-existing-id2");

        // Act: Retrieve the users by the non-existing IDs
        List<User> users = userService.findUsersByIds(nonExistingIds);

        // Assert: Verify the retrieved list is empty
        assertTrue(users.isEmpty(), "Expected no users to be found");
    }

    @Test
    void testUpdateUserStatus_ExistingUser_ShouldUpdateStatus() {
        // Arrange: Create and save a user
        User user = userRepository.save(User.builder()
                .userName("statususer")
                .email("statususer@example.com")
                .password("Test@12345678")
                .locked(false)
                .role(Role.USER)
                .build());

        // Act: Update the user's status
        userService.updateUserStatus(user.getId(), true, Role.ADMIN);

        // Assert: Verify the user's status was updated correctly
        User updatedUser = userService.findByUserId(user.getId());
        assertTrue(updatedUser.isLocked(), "Expected user to be locked");
        assertEquals(Role.ADMIN, updatedUser.getRole(), "Expected user role to be updated to ADMIN");
    }
}
