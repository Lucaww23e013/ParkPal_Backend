package at.technikum.parkpalbackend.RepositoryTests;


import at.technikum.parkpalbackend.model.*;
import at.technikum.parkpalbackend.persistence.MediaRepository;
import at.technikum.parkpalbackend.persistence.UserRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Disabled
class MediaRepositoryTest {

    @Autowired
    public MediaRepositoryTest(MediaRepository mediaRepository, UserRepository userRepository) {
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
    }

    private MediaRepository mediaRepository;

    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void MediaRepository_saveAllMedia(){
        User testUser = adminUser;

        userRepository.save(testUser);

        Media media = new Media(UUID.randomUUID().toString(), testUser, MediaCategory.AUDIO);

        Media savedMedia = mediaRepository.save(media);

        assertThat(savedMedia).isNotNull();
    }
    @Test
    void MediaRepository_findMediaByMediaId() {
        Media media = testMedia;
        mediaRepository.save(media);

        Media foundMedia = mediaRepository.findMediaByMediaId(media.getMediaId()).orElseThrow();

        assertEquals(foundMedia.getMediaId(), media.getMediaId());
        assertEquals(foundMedia.getMediaCategory(), media.getMediaCategory());

        assertEquals(foundMedia.getUser().getUserId(), media.getUser().getUserId());
        assertEquals(foundMedia.getUser().getPassword(), media.getUser().getPassword());
        assertEquals(foundMedia.getUser().getEmail(), media.getUser().getEmail());
        assertEquals(foundMedia.getUser().getFirstName(), media.getUser().getFirstName());
        assertEquals(foundMedia.getUser().getLastName(), media.getUser().getLastName());
        assertEquals(foundMedia.getUser().getSalutation(), media.getUser().getSalutation());
        assertEquals(foundMedia.getUser().getCountry().getCountryId(), media.getUser().getCountry().getCountryId());
        assertEquals(foundMedia.getUser().getCountry().getName(), media.getUser().getCountry().getName());
        assertEquals(foundMedia.getUser().getCountry().getIso2Code(), media.getUser().getCountry().getIso2Code());

        /*List<Event> foundMediaEvents = foundMedia.getUser().getJoinedEvents();
        List<Event> mediaEvents = media.getUser().getJoinedEvents();
        Compare later*/




    }

    @Test
    void MediaRepository_findMediaByUser() {
        Media media = testMedia;
        mediaRepository.save(media);

        Media foundMedia = mediaRepository.findMediaByUser(media.getUser()).orElseThrow();

        assertEquals(foundMedia.getMediaId(), media.getMediaId());
        assertEquals(foundMedia.getMediaCategory(), media.getMediaCategory());

        assertEquals(foundMedia.getUser().getUserId(), media.getUser().getUserId());
        assertEquals(foundMedia.getUser().getPassword(), media.getUser().getPassword());
        assertEquals(foundMedia.getUser().getEmail(), media.getUser().getEmail());
        assertEquals(foundMedia.getUser().getFirstName(), media.getUser().getFirstName());
        assertEquals(foundMedia.getUser().getLastName(), media.getUser().getLastName());
        assertEquals(foundMedia.getUser().getSalutation(), media.getUser().getSalutation());
        assertEquals(foundMedia.getUser().getCountry().getCountryId(), media.getUser().getCountry().getCountryId());
        assertEquals(foundMedia.getUser().getCountry().getName(), media.getUser().getCountry().getName());
        assertEquals(foundMedia.getUser().getCountry().getIso2Code(), media.getUser().getCountry().getIso2Code());
    }

    @Test
    void MediaRepository_deleteMedia() {
        Media media = testMedia;

        mediaRepository.save(media);

        mediaRepository.delete(media);

        Media deletedMedia = mediaRepository.findMediaByMediaId(media.getMediaId()).orElse(null);
        Assertions.assertThat(deletedMedia).isNull();
    }
}