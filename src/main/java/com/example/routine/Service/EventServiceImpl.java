package com.example.routine.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.routine.Model.Event;
import com.example.routine.Repository.EventRepository;
import com.example.routine.exception.EventNotFoundException;

@Service
public class EventServiceImpl implements EventService{
	@Autowired
	private EventRepository eventRepository;
	
	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	@Override
	public void deleteById(Long id) {
		try {
			//eventRepository.delete(findById(id));
			eventRepository.deleteById(id);
		//eventRepository.deleteById(id);
		} catch (Exception ex) {
			throw new EventNotFoundException(id);
		}
		
	}
	@Override
	public Event findById(Long id) {
		// TODO Auto-generated method stub
		return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
	}
	@Override
	public void updateEvent(Event event) {
		Event updatedEvent = findById(event.getId());
		updatedEvent.setDescription(event.getDescription());
		updatedEvent.setTime(event.getTime());
		eventRepository.save(updatedEvent);
		
	}

}
