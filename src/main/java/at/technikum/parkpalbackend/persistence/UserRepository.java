package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUserName(String userName);
}
