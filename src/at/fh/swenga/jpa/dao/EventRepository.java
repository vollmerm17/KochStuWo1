package at.fh.swenga.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.EventModel;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<EventModel, Integer> {

	@Transactional
	EventModel findFirstByEventName(String eventName);
	

	
	public EventModel findEventByEventId(int id);

	
}
