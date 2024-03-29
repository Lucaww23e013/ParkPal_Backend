package at.technikum.parkpalbackend.MapperTests;

import at.technikum.parkpalbackend.dto.MediaDto;
import at.technikum.parkpalbackend.mapper.MediaMapper;
import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static at.technikum.parkpalbackend.TestFixtures.normalUser;
import static org.junit.jupiter.api.Assertions.*;

class MediaMapperTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenDtoValue_thenMediaValue() {
        MediaMapper mediaMapper = new MediaMapper();
        String mediaId = UUID.randomUUID().toString();
        User test = normalUser;

        Media media = new Media(mediaId, test);

        MediaDto mediaDto = mediaMapper.toDto(media);

        assertEquals(mediaId, mediaDto.getMediaId());

        assertEquals(test.getUserId(), mediaDto.getUserId());
    }

    @Test
    void whenMediaValue_thenDtoValue() {MediaMapper mediaMapper = new MediaMapper();
        String mediaId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        MediaDto mediaDto = new MediaDto(mediaId, userId);

        Media media = mediaMapper.toEntity(mediaDto);

        assertEquals(mediaId, media.getMediaId());

        assertEquals(userId, media.getUser().getUserId());

    }
}