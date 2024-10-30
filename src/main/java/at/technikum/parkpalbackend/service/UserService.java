package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityAlreadyExistsException;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.enums.Role;
import at.technikum.parkpalbackend.persistence.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findByUserEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Email %s not found "
                        .formatted(email)));
    }

    @Transactional(readOnly = true)
    public User findByUserName(String name) {
        return userRepository.findUserByUserName(name)
                .orElseThrow(() -> new EntityNotFoundException("Username %s not found "
                .formatted(name)));
    }

    @Transactional(readOnly = true)
    public User findByUserNameOrEmail(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Username or email is not Valid");
        }

        if (input.contains("@")) {
            return findByUserEmail(input);
        } else {
            return findByUserName(input);
        }
    }


    public User findByUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User Id is not valid");
        }
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with Id %s not found "
                        .formatted(userId)));
    }

    @Transactional(readOnly = true)
    public List<User> findUsersByIds(List<String> userIds) {
        return userRepository.findAllById(userIds);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NoSuchElementException("No users found");
        }
        return users;
    }

    @Transactional
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (userEmailExists(user.getEmail())) {
            throw new EntityAlreadyExistsException("A user with the email " + user.getEmail() + " already exists.");
        }
        if (userNameExists(user.getUserName())) {
            throw new EntityAlreadyExistsException("A user with the username " + user.getUserName() + " already exists.");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User update(String userId, User newUser) {
        User existingUser = findByUserId(userId);
        // Check for unique username
        if (!existingUser.getUserName().equals(newUser.getUserName()) &&
                userNameExists(newUser.getUserName())) {
            throw new EntityAlreadyExistsException("A user with the username " +
                    newUser.getUserName() + " already exists.");
        }
        // Check for unique email
        if (!existingUser.getEmail().equals(newUser.getEmail()) &&
                userEmailExists(newUser.getEmail())) {
            throw new EntityAlreadyExistsException("A user with the email " +
                    newUser.getEmail() + " already exists.");
        }
        // Update fields of the existing user
        existingUser.setGender(newUser.getGender());
        existingUser.setSalutation(newUser.getSalutation());
        existingUser.setUserName(newUser.getUserName());
        existingUser.setFirstName(newUser.getFirstName());
        existingUser.setLastName(newUser.getLastName());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setCountry(newUser.getCountry());
        existingUser.setJoinedEvents(newUser.getJoinedEvents());
        existingUser.setMedia(newUser.getMedia());
        return userRepository.save(existingUser);
    }

    @Transactional
    public void delete(String userId) {
        User user = findByUserId(userId);
        user.getJoinedEvents().forEach(event -> event.getJoinedUsers().remove(user));
        userRepository.delete(user);
    }

    @Transactional
    public void updateUserStatus(String userId, Boolean locked, Role role) {
        User user = findByUserId(userId);
        if (locked != null) {
            user.setLocked(locked);
        }
        if (role != null) {
            user.setRole(role);
        }
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean userNameExists(String userName) {
        try {
            findByUserName(userName);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public boolean userEmailExists(String email) {
        try {
            findByUserEmail(email);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
