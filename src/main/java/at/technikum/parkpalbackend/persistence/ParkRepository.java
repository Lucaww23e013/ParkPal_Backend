package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkRepository extends JpaRepository<Park, String> {}
