package at.technikum.parkpalbackend.RepositoryTests;


import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class ParkRepositoryTest {

    @Autowired
    private ParkRepository parkRepository;

    Park park;
    Park savedPark;



    @BeforeEach
    void setUp() {
        park = parkWithEvents;
        savedPark = parkRepository.save(park);
        parkRepository.save(parkAwesome);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void ParkRepository_saveAllParks(){
        Assertions.assertThat(savedPark).isNotNull();
    }

    @Test
    void ParkRepository_findParkByParkId() {

        Park foundPark = parkRepository.findParkByParkId(park.getParkId()).orElseThrow();

        assertEquals(foundPark.getParkId(),park.getParkId());
        assertEquals(foundPark.getParkAddress().toString(),park.getParkAddress().toString());
        assertEquals(foundPark.getParkName(),park.getParkName());
        assertEquals(foundPark.getDescription(), park.getDescription());

        /*assertEquals(foundPark.getParkEvents().getFirst(), testPark.getParkEvents().getFirst());
        Assertions.assertThat(foundPark.getParkEvents()).isEqualTo(testPark.getParkEvents());
        assertArrayEquals(foundPark.getParkEvents().toArray(), testPark.getParkEvents().toArray());*/
    }

    @Test
    void ParkRepository_findByParkEventsIn() {
        parkRepository.save(park);

        Park foundPark = parkRepository.findByParkEventsIn(park.getParkEvents()).orElseThrow();

        assertEquals(foundPark.getParkId(),park.getParkId());
        assertEquals(foundPark.getParkAddress().toString(),park.getParkAddress().toString());
        assertEquals(foundPark.getParkName(),park.getParkName());
        assertEquals(foundPark.getDescription(), park.getDescription());
    }

    @Test
    void deleteParks() {
        parkRepository.save(park);

        parkRepository.delete(park);

        Park deletedPark = parkRepository.findParkByParkId(park.getParkId()).orElse(null);
        Assertions.assertThat(deletedPark).isNull();
    }
}