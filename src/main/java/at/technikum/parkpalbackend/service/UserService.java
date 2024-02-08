package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserName(String userName) {
        return userRepository.findUserByUserName(userName).orElseThrow();
    }

    public User save(User user) {
        return userRepository.save(user);
    }




    public User findUserByUserId(String userID) {
        return userRepository.findById(userID).orElseThrow(EntityNotFoundException::new);
    }
}
