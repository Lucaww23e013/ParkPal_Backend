package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.exception.UserWithUserNameOrEmailAlreadyExists;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Transactional(readOnly = true)
    public User findByUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with Id %s not found "
                        .formatted(userId)));
    }

    @Transactional(readOnly = true)
    public List<User> findUsersByIds(List<String> userIds) {
        List<User> users = userRepository.findAllById(userIds); // Find all users by IDs
        return users;
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
    public User create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            String constraintName = extractConstraintName(e);
            if ("unique_username".equals(constraintName)) {
                throw new UserWithUserNameOrEmailAlreadyExists("Username %s already exists."
                        .formatted(user.getUserName()));
            } else if ("unique_email".equals(constraintName)) {
                throw new UserWithUserNameOrEmailAlreadyExists("Email %s already exists."
                        .formatted(user.getEmail()));
            } else {
                throw e; // Rethrow if we cannot handle it here
            }
        }
    }

    private String extractConstraintName(DataIntegrityViolationException e) {
        String message = e.getMostSpecificCause().getMessage();
        Pattern pattern = Pattern.compile("'(unique_username|unique_email)'");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1); // Returns 'unique_username' or 'unique_email'
        }
        return "unknown"; // Default return value if no match is found
    }

    @Transactional
    public User update(String userId, User newUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with Id %s not found "
                        .formatted(userId)));
        BeanUtils.copyProperties(newUser, existingUser,
                userId, "id", "password", "role");

        return userRepository.save(existingUser);
    }

    @Transactional
    public void delete(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with Id %s not found "
                .formatted(userId)));
        userRepository.delete(user);
    }

    @Transactional
    public void lockUser(String userId) {
        setUserLockStatus(userId, true);
    }

    @Transactional
    public void unlockUser(String userId) {
        setUserLockStatus(userId, false);
    }

    private void setUserLockStatus(String userId, boolean locked) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with Id %s not found "
                        .formatted(userId)));
        user.setLocked(locked);
        userRepository.save(user);
    }

}
