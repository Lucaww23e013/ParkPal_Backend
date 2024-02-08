package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.repository.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public List<Media> findAllMedia() {
        return mediaRepository.findAll();
    }

    public Media findMediaByMediaId(String mediaId) {
        return mediaRepository.findMediaByMediaId(mediaId).orElseThrow();
    }

    public List<Media> getMediaByUser(User user) {
        return mediaRepository.findMediaByUser(user).stream().toList();
    }

    public Media save(Media createdMedia) {
        return mediaRepository.save(createdMedia);
    }

    public Media updateMedia(String mediaId, Media updatedMedia) {
        Media media = mediaRepository.findMediaByMediaId(mediaId).orElseThrow();

        media.setMediaId(updatedMedia.getMediaId());
        media.setUser(updatedMedia.getUser());
        media.setMediaCategory(updatedMedia.getMediaCategory());


        return mediaRepository.save(media);
    }

    public Media deleteMediaByMediaId(String mediaId) {
        Media media = mediaRepository.findMediaByMediaId(mediaId).orElseThrow();
        mediaRepository.delete(media);
        return null;
    }
}
