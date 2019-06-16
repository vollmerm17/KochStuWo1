package at.fh.swenga.jpa.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.EventRepository;
import at.fh.swenga.jpa.model.DietModel;
import at.fh.swenga.jpa.model.DormModel;
import at.fh.swenga.jpa.model.EventModel;
import at.fh.swenga.jpa.model.StudentModel;

@Controller
public class EventController {
	
	@Autowired
	EventRepository eventRepository;

	@RequestMapping(value = { "/addEvent" }, method = RequestMethod.GET)
	public String handleAddEvent() {
		return "addEvent";
	}

	@RequestMapping(value = { "/eventInfo" }, method = RequestMethod.GET)
	public String handleEventInfo() {
		return "eventInfo";
	}

	@RequestMapping(value = { "/eventsAttending" }, method = RequestMethod.GET)
	public String handleEventsAttending() {
		return "eventsAttending";
	}

	@RequestMapping(value = { "/eventsOwn" }, method = RequestMethod.GET)
	public String handleEventsOwn() {
		return "eventsOwn";
	}

	@PostMapping(value = { "/addEvent" })
	public String addEvent(Model model, @RequestParam String name, @RequestParam String description, @RequestParam Date date, @RequestParam Date time, DormModel dorm,DietModel diet,@RequestParam int attendeesMax, StudentModel student) {

		EventModel event1 = new EventModel(name, description,date, time, dorm,diet, attendeesMax, student);
		eventRepository.save(event1);

		return "index";
	}

}
