package at.fh.swenga.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.EventModel;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<EventModel, Integer> {

	@Transactional
	EventModel findFirstByEventName(String eventName);
	
	EventModel findEventByEventId(int id);
	
	//@Query("SELECT e FROM EventModel e WHERE e.users_userId = :userid")
	List<EventModel> findEventByUserUserId(int userId);
	
	List<EventModel> findEventByStudentsId(int id);

	
}
