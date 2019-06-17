package at.fh.swenga.jpa.controller;

import org.springframework.stereotype.Controller;

@Controller
public class EventController {}
	/*
	@Autowired
	EventRepository eventRepository;

	@Autowired
	DietRepository dietRepository;

	@Autowired
	DormRepository dormRepository;
	
	@Autowired
	StudentRepository studentRepository;}

	
	@RequestMapping(value = { "/addEvent" }, method = RequestMethod.GET)
	public String handleAddEvent(Model model) {
		
		List<DormModel> dorms = dormRepository.findAll();
		model.addAttribute("dorms", dorms);

		List<DietModel> diets = dietRepository.findAll();
		model.addAttribute("diets", diets);
		
		
		return "addEvent";
	}
	
	@PostMapping("/addEvent")
	public String register (@Valid EventModel event, @Valid StudentModel student, BindingResult bindingResult, Model model) throws ParseException {

		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid: " + fieldError.getCode() + "<br>";
			}

			model.addAttribute("errorMessage", errorMessage);
			return "register";
		}

		EventModel event1 = eventRepository.findFirstByName(event.getName());
		StudentModel student1 = studentRepository.findFirstByFirstName(student.getFirstName());


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
			event1.setDorm(event.getDorm());
			event1.setDiet(event.getDiet());
			event1.setStudent(student1);

			eventRepository.save(event1);

			return "index";
			
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

	
	 * @PostMapping(value = { "/addEvent" }) public String addEvent(Model
	 * model, @RequestParam String name, @RequestParam String
	 * description, @RequestParam Date date, @RequestParam Date time, DormModel
	 * dorm,DietModel diet,@RequestParam int attendeesMax, StudentModel student) {
	 * 
	 * EventModel event1 = new EventModel(name, description,date, time, dorm,diet,
	 * attendeesMax, student); eventRepository.save(event1);
	 * 
	 * return "index"; }
	 
}
*/