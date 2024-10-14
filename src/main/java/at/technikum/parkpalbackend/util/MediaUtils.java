package at.technikum.parkpalbackend.util;

import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.service.FileService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MediaUtils {

    private final FileService fileService;
    public MediaUtils(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Retrieves a list of File objects based on a list of external IDs.
     *
     * @param mediaFileExternalIds List of external IDs of files.
     * @return List of File objects.
     */
    public List<File> getFileList(List<String> mediaFileExternalIds) {
        if (mediaFileExternalIds == null) {
            return new ArrayList<>();
        }

        return mediaFileExternalIds.stream()
                .map(fileService::findFileByExternalId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Sets the event reference for each File object in the provided list.
     *
     * @param event      The Event to associate with the files.
     * @param mediaFiles List of File objects to be updated.
     */
    public void setEventMedia(Event event, List<File> mediaFiles) {
        mediaFiles.stream()
                .filter(Objects::nonNull)
                .forEach(file -> {
                    file.setEvent(event);
                    fileService.save(file);
                });
    }

    /**
     * Updates the event's media files. This includes removing old files and associating new files.
     *
     * @param event        The Event to update.
     * @param newMediaFiles List of new File objects to associate with the event.
     */
    public void updateEventMedia(Event event, List<File> newMediaFiles) {
        // Remove old associations
        event.getMedia().stream()
                .filter(file -> !newMediaFiles.contains(file))
                .forEach(file -> file.setEvent(null));

        // Set the new event reference for new media files
        setEventMedia(event, newMediaFiles);
    }

    /**
     * Sets the park reference for each File object in the provided list.
     *
     * @param park       The Park to associate with the files.
     * @param mediaFiles List of File objects to be updated.
     */
    public void setParkMedia(Park park, List<File> mediaFiles) {
        mediaFiles.stream()
                .filter(Objects::nonNull)
                .forEach(file -> {
                    file.setPark(park);
                    fileService.save(file);
                });
    }
}