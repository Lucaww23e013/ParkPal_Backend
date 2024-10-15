package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.exception.UserWithUserNameOrEmailAlreadyExists;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.enums.Role;
import at.technikum.parkpalbackend.persistence.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void whenFindUserByEmail_thenReturnUser() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        when(userRepository.findUserByEmail(TestFixtures.adminUser.getEmail())).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findByUserEmail(TestFixtures.adminUser.getEmail());

        // Assert
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(TestFixtures.adminUser.getEmail(), foundUser.getEmail());
        verify(userRepository).findUserByEmail(TestFixtures.adminUser.getEmail());
    }

    @Test
    void whenFindUserWithNullEmail_thenThrowEntityNotFoundException() {
        // Arrange
        String email = null;
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.findByUserEmail(email));

        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void whenFindUserByUserName_thenReturnUser() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        when(userRepository.findUserByUserName(TestFixtures.adminUser.getUserName())).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findByUserName(TestFixtures.adminUser.getUserName());

        // Assert
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(TestFixtures.adminUser.getUserName(), foundUser.getUserName());
        verify(userRepository).findUserByUserName(TestFixtures.adminUser.getUserName());
    }

    @Test
    void whenFindUserWithNullUsername_thenThrowEntityNotFoundException() {
        // Arrange
        String username = null;
        when(userRepository.findUserByUserName(username)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findByUserName(username));

        verify(userRepository).findUserByUserName(username);
    }

    @Test
    void whenFindUserByUserId_thenReturnUser() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        when(userRepository.findById(TestFixtures.adminUser.getId())).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findByUserId(TestFixtures.adminUser.getId());

        // Assert
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(TestFixtures.adminUser.getId(), foundUser.getId());
        verify(userRepository).findById(TestFixtures.adminUser.getId());
    }


    @Test
    void whenFindUserWithUserIdNull_thenThrowEntityNotFoundException() {
        // Arrange
        String userId = null;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.findByUserId(userId));

        verify(userRepository).findById(userId);
    }

    @Test
    void whenFindAllUsers_thenReturnAllUsers() {
        // Arrange
        User user1 = TestFixtures.adminUser;
        user1.setId(UUID.randomUUID().toString());
        User user2 = TestFixtures.normalUser;
        user2.setId(UUID.randomUUID().toString());
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user1);
        expectedUsers.add(user2);

        when(userRepository.findAll()).thenReturn(expectedUsers);
        // Act
        List<User> foundUsers = userService.findAll();
        // Assert
        Assertions.assertNotNull(foundUsers);
        Assertions.assertEquals(expectedUsers, foundUsers);
        verify(userRepository).findAll();
    }

    @Test
    void whenFindAllUsers_andNoUsersAreFound_thenThrowNoSuchElementException() {
        // Arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userService.findAll());

        verify(userRepository).findAll();
    }

    @Test
    void whenSaveUser_thenReturnUser() {
        //Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        when(userRepository.save(user)).thenReturn(user);

        //Act
        User savedUser = userService.save(user);

        //Assert
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(user, savedUser);
        verify(userRepository).save(user);

    }

    @Test
    void whenUserToSaveNull_thenThrowIllegalArgumentException() {
        // Arrange
        User user = null;

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenUpdateUser_thenReturnUpdatedUser() {
        // Arrange
        User existingUser = TestFixtures.adminUser;
        existingUser.setId(UUID.randomUUID().toString());
        existingUser.setEmail("old@test.com");
        existingUser.setPassword("OldPassword");

        User newUser = TestFixtures.adminUser;
        newUser.setEmail("new@test.com");
        newUser.setPassword("NewPassword");

        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        User updatedUser = userService.update(existingUser.getId(), newUser);

        // Assert
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newUser.getEmail(), updatedUser.getEmail());
        Assertions.assertEquals(newUser.getPassword(), updatedUser.getPassword());
        verify(userRepository).findById(existingUser.getId());
        verify(userRepository).save(existingUser);
    }

    @Test
    void whenUpdateNonExistentUser_thenThrowEntityNotFoundException() {
        // Arrange
        User newUser = TestFixtures.adminUser;
        newUser.setId(UUID.randomUUID().toString());
        newUser.setEmail("new@test.com");
        newUser.setPassword("NewPassword");

        when(userRepository.findById(newUser.getId())).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.update(newUser.getId(), newUser));
        verify(userRepository).findById(newUser.getId());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenDeleteUser_thenUserIsDeleted() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user)).thenReturn(Optional.empty());
        // Mock the delete method to do nothing
        doNothing().when(userRepository).delete(user);

        // Act
        userService.delete(user.getId());

        // Assert
        verify(userRepository).findById(user.getId());
        verify(userRepository).delete(user);

        Optional<User> deletedUserOptional = userRepository.findById(user.getId());
        Assertions.assertFalse(deletedUserOptional.isPresent());
    }

    @Test
    void whenDeleteNonExistentUser_thenThrowEntityNotFoundException() {
        // Arrange
        String userId = UUID.randomUUID().toString();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.delete(userId));
        verify(userRepository).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void whenFindByUserNameOrEmail_withEmail_thenReturnUser() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        String email = user.getEmail();
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findByUserNameOrEmail(email);

        // Assert
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(email, foundUser.getEmail());
        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void whenFindByUserNameOrEmail_withUsername_thenReturnUser() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        String username = user.getUserName();
        when(userRepository.findUserByUserName(username)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findByUserNameOrEmail(username);

        // Assert
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(username, foundUser.getUserName());
        verify(userRepository).findUserByUserName(username);
    }

    @Test
    void whenFindByUserNameOrEmail_withInvalidInput_thenThrowIllegalArgumentException() {
        // Arrange
        String input = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.findByUserNameOrEmail(input));
    }

    // Test for findUsersByIds
    @Test
    void whenFindUsersByIds_thenReturnUsers() {
        // Arrange
        User user1 = TestFixtures.adminUser;
        user1.setId(UUID.randomUUID().toString());
        User user2 = TestFixtures.normalUser;
        user2.setId(UUID.randomUUID().toString());
        List<String> userIds = Arrays.asList(user1.getId(), user2.getId());
        List<User> expectedUsers = Arrays.asList(user1, user2);

        when(userRepository.findAllById(userIds)).thenReturn(expectedUsers);

        // Act
        List<User> foundUsers = userService.findUsersByIds(userIds);

        // Assert
        Assertions.assertNotNull(foundUsers);
        Assertions.assertEquals(expectedUsers, foundUsers);
        verify(userRepository).findAllById(userIds);
    }

    // Test for save method exception handling
    @Test
    void whenSaveUserWithDuplicateUsername_thenThrowUserWithUserNameOrEmailAlreadyExists() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        DataIntegrityViolationException exception = new DataIntegrityViolationException(
                "duplicate key value violates unique constraint 'unique_username'");
        when(userRepository.save(user)).thenThrow(exception);

        // Act & Assert
        assertThrows(UserWithUserNameOrEmailAlreadyExists.class, () -> userService.save(user));
    }

    @Test
    void whenSaveUserWithDuplicateEmail_thenThrowUserWithUserNameOrEmailAlreadyExists() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        DataIntegrityViolationException exception = new DataIntegrityViolationException(
                "duplicate key value violates unique constraint 'unique_email'");
        when(userRepository.save(user)).thenThrow(exception);

        // Act & Assert
        assertThrows(UserWithUserNameOrEmailAlreadyExists.class, () -> userService.save(user));
    }

    @Test
    void whenSaveUserWithUnknownConstraint_thenThrowDataIntegrityViolationException() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        DataIntegrityViolationException exception = new DataIntegrityViolationException(
                "duplicate key value violates unknown constraint");
        when(userRepository.save(user)).thenThrow(exception);

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> userService.save(user));
    }

    // Test for extractConstraintName
    @Test
    void whenExtractConstraintName_thenReturnConstraintName() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        DataIntegrityViolationException exception = new DataIntegrityViolationException(
                "duplicate key value violates unique constraint 'unique_username'");

        // Mock the repository to throw the exception when saving
        when(userRepository.save(user)).thenThrow(exception);

        // Act & Assert
        UserWithUserNameOrEmailAlreadyExists thrownException = assertThrows(UserWithUserNameOrEmailAlreadyExists.class,
                () -> userService.save(user));

        // Verify the correct exception message that would result from the 'extractConstraintName' logic
        Assertions.assertEquals("Username " + user.getUserName() + " already exists.", thrownException.getMessage());
        verify(userRepository).save(user);
    }

    // Test for updateUserStatus
    @Test
    void whenUpdateUserStatus_thenUserStatusUpdated() {
        // Arrange
        User user = TestFixtures.adminUser;
        user.setId(UUID.randomUUID().toString());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Act
        userService.updateUserStatus(user.getId(), true, Role.ADMIN);

        // Assert
        Assertions.assertTrue(user.isLocked());
        Assertions.assertEquals(Role.ADMIN, user.getRole());
        verify(userRepository).findById(user.getId());
        verify(userRepository).save(user);
    }
}