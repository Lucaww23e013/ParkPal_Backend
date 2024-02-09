package at.technikum.parkpalbackend.RepositoryTests;

import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.EventRepository;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static at.technikum.parkpalbackend.TestFixtures.grilling;
import static org.assertj.core.api.Assumptions.assumeThat;


@DataJpaTest
//@Disabled
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ParkRepository parkRepository;

    @BeforeEach
    void setUp() {
        assumeThat(eventRepository).isNotNull();
        assumeThat(parkRepository).isNotNull();
    }

    @Test
    void whenEventCreate_saveToDateBase() {
        // when
        Event grillEvent = grilling;
        Park park = grillEvent.getPark();
        // act
        Park savedPark = parkRepository.saveAndFlush(park);
        Event saved = eventRepository.saveAndFlush(grillEvent);
        // then
        assumeThat(savedPark.getParkId()).isSameAs(park.getParkId());
        assumeThat(saved).isSameAs(grillEvent);
        assumeThat(saved.getEventId()).isNotNull();

    }

}
