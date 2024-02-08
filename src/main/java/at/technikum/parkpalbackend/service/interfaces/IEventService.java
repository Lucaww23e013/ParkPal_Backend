package at.technikum.parkpalbackend.service.interfaces;

import at.technikum.parkpalbackend.model.Event;

import java.util.List;

public interface IEventService {

    Event save(Event event);
    List<Event> findAllEvents();
    Event findByEventId(String eventId);
    List<Event> findAllEventsByPark(String parkId);
    List<Event> findAllEventsByUser(String userId);
    Event deleteEventById(String eventID);

}
