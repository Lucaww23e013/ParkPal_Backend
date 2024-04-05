package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    public User findByUserId(String userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = findByUserEmail(email); //username always equals email
        if (user.getPassword().equals(password)) return user;
        else {
            throw new EntityNotFoundException("User not found or invalid password");
        }
    }

    public User update(String userId, User newUser) {
        User existingUser = userRepository.findUserByUserId(userId)
                .orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyProperties(newUser, existingUser, userId);
        return userRepository.save(existingUser);
    }

    public void delete(String userId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
    }


}
