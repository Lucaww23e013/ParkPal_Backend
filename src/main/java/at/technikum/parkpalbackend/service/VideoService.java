package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.Video;
import at.technikum.parkpalbackend.persistence.VideoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<Video> findAllVideos() {
        return videoRepository.findAll();
    }

    public Video findVideoByVideoId(String videoId) {
        return videoRepository.findVideoByVideoId(videoId).orElseThrow();
    }

    public List<Video> findVideosByUser(User user) {
        return videoRepository.findVideoByUser(user).stream().toList();
    }
    public Video save(Video createdVideo) {
        return videoRepository.save(createdVideo);
    }

    public Video updateVideo(String videoId, Video updatedVideo) {
        Video video = videoRepository.findVideoByVideoId(videoId).orElseThrow();

        video.setVideoId(updatedVideo.getVideoId());
        video.setUser(updatedVideo.getUser());
        return videoRepository.save(video);
    }

    public Video deleteVideoByVideoId(String videoId) {
        Video video = videoRepository.findVideoByVideoId(videoId).orElseThrow();
        videoRepository.delete(video);
        return null;
    }

    public LocalDateTime getUploadDate() {
        return LocalDateTime.now();
    }
}
