package at.technikum.parkpalbackend.RepositoryTests;


import at.technikum.parkpalbackend.model.*;
import at.technikum.parkpalbackend.persistence.MediaRepository;
import at.technikum.parkpalbackend.persistence.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class MediaRepositoryTest {

    @Autowired
    public MediaRepositoryTest(MediaRepository mediaRepository, UserRepository userRepository) {
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
    }

    private MediaRepository mediaRepository;

    private UserRepository userRepository;

    User testUser;
    Media mediaTest;
    @BeforeEach
    void setUp() {
        testUser = adminUser;
        mediaTest = testMedia;
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void MediaRepository_saveAllMedia(){
        Media savedMedia = mediaRepository.save(mediaTest);

        assertThat(savedMedia).isNotNull();
    }
    @Test
    void MediaRepository_findMediaByMediaId() {
        mediaRepository.save(testMedia);

        Media foundMedia = mediaRepository.findMediaByMediaId(testMedia.getMediaId()).orElseThrow();

        assertEquals(foundMedia.getMediaId(), testMedia.getMediaId());
        assertEquals(foundMedia.getMediaCategory(), testMedia.getMediaCategory());

        assertEquals(foundMedia.getUser().getUserId(), testMedia.getUser().getUserId());
        assertEquals(foundMedia.getUser().getPassword(), testMedia.getUser().getPassword());
        assertEquals(foundMedia.getUser().getEmail(), testMedia.getUser().getEmail());
        assertEquals(foundMedia.getUser().getFirstName(), testMedia.getUser().getFirstName());
        assertEquals(foundMedia.getUser().getLastName(), testMedia.getUser().getLastName());
        assertEquals(foundMedia.getUser().getSalutation(), testMedia.getUser().getSalutation());
        assertEquals(foundMedia.getUser().getCountry().getCountryId(), testMedia.getUser().getCountry().getCountryId());
        assertEquals(foundMedia.getUser().getCountry().getName(), testMedia.getUser().getCountry().getName());
        assertEquals(foundMedia.getUser().getCountry().getIso2Code(), testMedia.getUser().getCountry().getIso2Code());

        /*List<Event> foundMediaEvents = foundMedia.getUser().getJoinedEvents();
        List<Event> mediaEvents = media.getUser().getJoinedEvents();
        Compare later*/




    }

    @Test
    void MediaRepository_findMediaByUser() {
        mediaRepository.save(testMedia);

        Media foundMedia = mediaRepository.findMediaByUser(testMedia.getUser()).orElseThrow();

        assertEquals(foundMedia.getMediaId(), testMedia.getMediaId());
        assertEquals(foundMedia.getMediaCategory(), testMedia.getMediaCategory());

        assertEquals(foundMedia.getUser().getUserId(), testMedia.getUser().getUserId());
        assertEquals(foundMedia.getUser().getPassword(), testMedia.getUser().getPassword());
        assertEquals(foundMedia.getUser().getEmail(), testMedia.getUser().getEmail());
        assertEquals(foundMedia.getUser().getFirstName(), testMedia.getUser().getFirstName());
        assertEquals(foundMedia.getUser().getLastName(), testMedia.getUser().getLastName());
        assertEquals(foundMedia.getUser().getSalutation(), testMedia.getUser().getSalutation());
        assertEquals(foundMedia.getUser().getCountry().getCountryId(), testMedia.getUser().getCountry().getCountryId());
        assertEquals(foundMedia.getUser().getCountry().getName(), testMedia.getUser().getCountry().getName());
        assertEquals(foundMedia.getUser().getCountry().getIso2Code(), testMedia.getUser().getCountry().getIso2Code());
    }

    @Test
    void MediaRepository_deleteMedia() {
        mediaRepository.save(testMedia);

        mediaRepository.delete(testMedia);

        Media deletedMedia = mediaRepository.findMediaByMediaId(testMedia.getMediaId()).orElse(null);
        Assertions.assertThat(deletedMedia).isNull();
    }
}