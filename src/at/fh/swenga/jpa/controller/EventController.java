
package at.fh.swenga.jpa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.DietRepository;
import at.fh.swenga.jpa.dao.DormRepository;
import at.fh.swenga.jpa.dao.EventPictureRepository;
import at.fh.swenga.jpa.dao.EventRepository;
import at.fh.swenga.jpa.dao.InstituteRepository;
import at.fh.swenga.jpa.dao.ProfilePictureRepository;
import at.fh.swenga.jpa.dao.StudentRepository;
import at.fh.swenga.jpa.dao.UserRepository;
import at.fh.swenga.jpa.model.DietModel;
import at.fh.swenga.jpa.model.DormModel;
import at.fh.swenga.jpa.model.EventModel;
import at.fh.swenga.jpa.model.EventPictureModel;
import at.fh.swenga.jpa.model.ProfilePictureModel;
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

	@Autowired
	EventPictureRepository eventPictureRepository;

	@Autowired
	ProfilePictureRepository profilePictureRepository;

	@InitBinder
	public void initDateBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}



	@GetMapping("/addEvent")
	public String handleAddEvent(Model model, Authentication aut) {

		List<DormModel> dorms = dormRepository.findAll();
		model.addAttribute("dorms", dorms);

		List<DietModel> diets = dietRepository.findAll();
		model.addAttribute("diets", diets);

		UserModel user = userRepository.findFirstByUserName(aut.getName());
		StudentModel student = studentRepository.findStudentByUserUserId(user.getUserId());

		if (student != null) {

			model.addAttribute("student", student);
			if (student.getPicture() != null) {

				Optional<ProfilePictureModel> ppOpt = profilePictureRepository.findById(student.getPicture().getId());
				ProfilePictureModel pp = ppOpt.get();
				byte[] profilePicture = pp.getContent();

				StringBuilder sb = new StringBuilder();
				sb.append("data:image/png;base64,");
				sb.append(Base64.encodeBase64String(profilePicture));
				String image = sb.toString();
				model.addAttribute("image", image);

			}
		}

		return "addEvent";
	}

	@Transactional
	@PostMapping("/addEvent")
	public String addEvent(@Valid EventModel event, BindingResult bindingResult, Model model,
			@RequestParam(value = "dormId") int dormId, @RequestParam(value = "dietId") int dietId, Authentication aut)
			throws ParseException {

		/*
		 * if (bindingResult.hasErrors()) { String errorMessage = ""; for (FieldError
		 * fieldError : bindingResult.getFieldErrors()) { errorMessage +=
		 * fieldError.getField() + " is invalid: " + fieldError.getCode() + "<br>"; }
		 *
		 * model.addAttribute("errorMessage", errorMessage); return "addEvent"; }
		 */

		EventModel event1 = eventRepository.findFirstByEventName(event.getName());

		UserModel user1 = userRepository.findFirstByUserName(aut.getName());
		DormModel dorm1 = dormRepository.getOne(dormId);
		DietModel diet1 = dietRepository.getOne(dietId);

		if (event1 != null) {
			model.addAttribute("errorMessage", "A event with this name already exists!<br>");
		} else {

			event1 = new EventModel();
			event1.setName(event.getName());
			event1.setDescription(event.getDescription());
			event1.setDayOfEvent(event.getDayOfEvent());
			event1.setTimeOfEvent(event.getTimeOfEvent());
			event1.setAttendeesMax(event.getAttendeesMax());
			event1.setDorm(dorm1);
			event1.setDiet(diet1);
			event1.setUser(user1);
			eventRepository.save(event1);

			model.addAttribute("message", "Your event was created successfully, have fun!<br>");
			
			return "addEvent";

		}

		return "addEvent";
		
		

	}

	@RequestMapping(value = { "/eventInfo" }, method = RequestMethod.GET)
	public String handleEventInfo(Model model, @RequestParam("eventId") int eventId, Authentication aut) {
		model.addAttribute("eventId", eventId);


		EventModel event = eventRepository.findEventByEventId(eventId);
		model.addAttribute("event", event);
		
		if (event != null) {

			if (event.getPicture() != null) {
				Optional<EventPictureModel> ppOpt = eventPictureRepository.findById(event.getPicture().getId());
				EventPictureModel pp = ppOpt.get();
				byte[] eventPicture = pp.getContent();

				StringBuilder sb = new StringBuilder();
				sb.append("data:image/png;base64,");
				sb.append(Base64.encodeBase64String(eventPicture));
				String image = sb.toString();
				model.addAttribute("image", image);

			}
		} else {
			model.addAttribute("errorMessage", "Something went wrong!");
			return "login";
		}

		return "eventInfo";
	}

	@RequestMapping(value = { "/eventsAttending" }, method = RequestMethod.GET)
	public String handleEventsAttending(Authentication aut, Model model) {
		UserModel user = userRepository.findFirstByUserName(aut.getName());
		StudentModel student = studentRepository.findStudentByUserUserId(user.getUserId());

		if (student != null) {

			model.addAttribute("student", student);
			if (student.getPicture() != null) {

				Optional<ProfilePictureModel> ppOpt = profilePictureRepository.findById(student.getPicture().getId());
				ProfilePictureModel pp = ppOpt.get();
				byte[] profilePicture = pp.getContent();

				StringBuilder sb = new StringBuilder();
				sb.append("data:image/png;base64,");
				sb.append(Base64.encodeBase64String(profilePicture));
				String image = sb.toString();
				model.addAttribute("image", image);

			}
		}

		UserModel user1 = userRepository.findFirstByUserName(aut.getName());
		List<EventModel> events = eventRepository.findEventByStudentsId(user1.getUserId());
		if (events.isEmpty()) {

			model.addAttribute("warningMessage", "You are not attending any events yet!<br>");
			return "forward:index";
		}
		model.addAttribute("events", events);
		return "eventsAttending";
	}

	@RequestMapping(value = { "/eventsOwn" }, method = RequestMethod.GET)
	public String handleEventsOwn(Authentication aut, Model model) {

		UserModel user = userRepository.findFirstByUserName(aut.getName());
		StudentModel student = studentRepository.findStudentByUserUserId(user.getUserId());

		if (student != null) {

			model.addAttribute("student", student);
			if (student.getPicture() != null) {

				Optional<ProfilePictureModel> ppOpt = profilePictureRepository.findById(student.getPicture().getId());
				ProfilePictureModel pp = ppOpt.get();
				byte[] profilePicture = pp.getContent();

				StringBuilder sb = new StringBuilder();
				sb.append("data:image/png;base64,");
				sb.append(Base64.encodeBase64String(profilePicture));
				String image = sb.toString();
				model.addAttribute("image", image);

			}
		}

		UserModel user1 = userRepository.findFirstByUserName(aut.getName());
		List<EventModel> events = eventRepository.findEventByUserUserId(user1.getUserId());
		if (events.isEmpty()) {

			model.addAttribute("warningMessage", "You don't have any own events yet!<br>");

		}
		model.addAttribute("events", events);

		return "eventsOwn";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "404";

	}

	@GetMapping("/attend")
	public String attenToEvent(Authentication aut, Model model, @RequestParam int eventId) {

		UserModel user1 = userRepository.findFirstByUserName(aut.getName());
		StudentModel student1 = user1.getStudent();
		Optional<EventModel> eventO = eventRepository.findById(eventId);

		if (eventO.isPresent()) {
			EventModel event1 = eventO.get();

			System.out.println();
			if (event1.getAttendeesMax() > 0 && !event1.getStudents().contains(student1)) {

				event1.addStudi(student1);
				event1.setAttendeesMax(event1.getAttendeesMax() - 1);
				eventRepository.save(event1);
				model.addAttribute("message", "Have fun on the event!<br>");
				return "forward:index";
			} else {

				if (event1.getAttendeesMax() > 0) {
					model.addAttribute("errorMessage", "Sorry this event is already full, maybe next time!<br>");
				} else {
					model.addAttribute("warningMessage",
							"Sorry you are already attending this event. See you soon!<br>");
				}

			}

		}
		return "forward:index";
	}
}
