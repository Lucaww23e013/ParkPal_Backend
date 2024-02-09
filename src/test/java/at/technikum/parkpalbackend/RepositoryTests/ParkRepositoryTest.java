package at.technikum.parkpalbackend.RepositoryTests;


import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParkRepositoryTest {

    @Autowired
    private ParkRepository parkRepository;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void ParkRepository_saveAllParks(){
        Park park = parkAwesome;

        Park savedPark = parkRepository.save(park);

        Assertions.assertThat(savedPark).isNotNull();
    }

    @Test
    void ParkRepository_findParkByParkId() {
        Park testPark = parkAwesome;
        parkRepository.save(parkAwesome);

        Park foundPark = parkRepository.findParkByParkId(testPark.getParkId()).orElseThrow();

        assertEquals(foundPark.getParkId(),testPark.getParkId());
        assertEquals(foundPark.getParkAddress().toString(),testPark.getParkAddress().toString());
        assertEquals(foundPark.getParkName(),testPark.getParkName());
        assertEquals(foundPark.getDescription(), testPark.getDescription());

        /*assertEquals(foundPark.getParkEvents().getFirst(), testPark.getParkEvents().getFirst());
        Assertions.assertThat(foundPark.getParkEvents()).isEqualTo(testPark.getParkEvents());
        assertArrayEquals(foundPark.getParkEvents().toArray(), testPark.getParkEvents().toArray());*/
    }

    @Test
    void ParkRepository_findByParkEventsIn() {
        Park testPark = parkWithEvents;
        parkRepository.save(parkWithEvents);

        Park foundPark = parkRepository.findByParkEventsIn(testPark.getParkEvents()).orElseThrow();

        assertEquals(foundPark.getParkId(),testPark.getParkId());
        assertEquals(foundPark.getParkAddress().toString(),testPark.getParkAddress().toString());
        assertEquals(foundPark.getParkName(),testPark.getParkName());
        assertEquals(foundPark.getDescription(), testPark.getDescription());
    }

    @Test
    void deleteParks() {
        Park park = parkAwesome;

        parkRepository.save(park);

        parkRepository.delete(park);

        Park deletedPark = parkRepository.findParkByParkId(park.getParkId()).orElse(null);
        Assertions.assertThat(deletedPark).isNull();
    }
}