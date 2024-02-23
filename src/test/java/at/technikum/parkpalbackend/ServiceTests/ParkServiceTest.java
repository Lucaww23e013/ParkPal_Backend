package at.technikum.parkpalbackend.ServiceTests;

import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import at.technikum.parkpalbackend.service.ParkService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static at.technikum.parkpalbackend.TestFixtures.*;

@Disabled
class ParkServiceTest {
    public ParkService parkService;
    public ParkRepository parkRepository;

    public Park park;

    public ParkServiceTest(ParkService parkService, ParkRepository parkRepository, Park park) {
        this.parkService = parkService;
        this.parkRepository = parkRepository;
        this.park = park;
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllParks() {
       /* List<Park> savedParks = new ArrayList<>();
        savedParks.add(parkWithEvents);
        savedParks.add(parkAwesome);
        savedParks.add(parkLuca);
        List<Park> foundParks = parkRepository.findAll();

        assertEquals(savedParks, foundParks);*/

    }

    @Test
    void findParkByParkId() {
    }

    @Test
    void save() {
       /* ParkService parkService = new ParkService(parkRepository);
        Park savedPark = parkWithEvents;
        parkService.save(parkWithEvents);
        assertEquals(savedPark, parkWithEvents);*/
    }

    @Test
    void updatePark() {
        /*Park updatedPark = parkService.updatePark(parkWithEvents.getParkId(), parkWithEvents);
        assertEquals(updatedPark, parkWithEvents);*/
    }

    @Test
    void deleteParkByParkId() {
      /*  park = parkWithEvents;
        parkRepository.save(park);
        parkService.deleteParkByParkId(park.getParkId());

        assertNull(park);*/
    }

    @Test
    void findParkByEvents() {
        parkService.findParkByEvents(eventList);
    }
}