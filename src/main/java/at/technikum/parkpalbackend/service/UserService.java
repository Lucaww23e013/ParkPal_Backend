package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    public User findByUserName(String name) {
        return userRepository.findUserByUserName(name).orElseThrow(EntityNotFoundException::new);
    }

    public User findByUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NoSuchElementException("No users found");
        }
        return users;
    }

    public User create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);
    }

    public User update(String userId, User newUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyProperties(newUser, existingUser, userId);
        return userRepository.save(existingUser);
    }

    public void delete(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
    }


}
