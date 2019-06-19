package at.fh.swenga.jpa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.DietRepository;
import at.fh.swenga.jpa.dao.DormRepository;
import at.fh.swenga.jpa.dao.EventRepository;
import at.fh.swenga.jpa.dao.InstituteRepository;
import at.fh.swenga.jpa.dao.StudentRepository;
import at.fh.swenga.jpa.dao.UserRepository;
import at.fh.swenga.jpa.model.DietModel;
import at.fh.swenga.jpa.model.DormModel;
import at.fh.swenga.jpa.model.EventModel;
import at.fh.swenga.jpa.model.InstituteModel;
import at.fh.swenga.jpa.model.StudentModel;
import at.fh.swenga.jpa.model.UserModel;

@Controller
public class EventController {
	
	@Autowired
	EventRepository eventRepository;

	@Autowired
	DietRepository dietRepository;

	@Autowired
	DormRepository dormRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	InstituteRepository instituteRepository;
	
	@InitBinder
	public void initDateBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	
	@GetMapping("/addEvent" )
	public String handleAddEvent(Model model) {
		
		List<DormModel> dorms = dormRepository.findAll();
		model.addAttribute("dorms", dorms);

		List<DietModel> diets = dietRepository.findAll();
		model.addAttribute("diets", diets);
		
		
		return "addEvent";
	}
	
	@Transactional
	@PostMapping("/addEvent")
	public String register (@Valid EventModel event, @Valid StudentModel student,UserModel user, BindingResult bindingResult, Model model,  @RequestParam(value="dormId") int dormId, @RequestParam(value="dietId") int dietId) throws ParseException {

		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid: " + fieldError.getCode() + "<br>";
			}

			model.addAttribute("errorMessage", errorMessage);
			return "addEvent";
		}

		EventModel event1 = eventRepository.findFirstByEventName(event.getName());
		String userName = System.getProperty("user.userName");
		UserModel user1 = userRepository.findFirstByUserName(userName);
		StudentModel student1 = userRepository.findStudentById(user1);
		
		System.out.println(student1);

		DormModel dorm1 = dormRepository.getOne(dormId);
		DietModel diet1 = dietRepository.getOne(dietId);

		if (event1 != null) {
			model.addAttribute("errorMessage", "A event with this name already exists!<br>");

		}

		else {

			event1 = new EventModel();
			event1.setName(event.getName());
			event1.setDescription(event.getDescription());
			event1.setDayOfEvent(event.getDayOfEvent());
			event1.setTimeOfEvent(event.getTimeOfEvent());
			event1.setAttendeesMax(event.getAttendeesMax());
			event1.setDorm(dorm1);
			event1.setDiet(diet1);
			event1.setStudent(student1);

			eventRepository.save(event1);

			return "addEvent";
			
		}
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

}
