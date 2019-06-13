package at.fh.swenga.jpa.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.DietRepository;
import at.fh.swenga.jpa.dao.DormRepository;
import at.fh.swenga.jpa.dao.EventRepository;
import at.fh.swenga.jpa.dao.InstituteRepository;
import at.fh.swenga.jpa.dao.PositionRepository;
import at.fh.swenga.jpa.dao.StudentRepository;
import at.fh.swenga.jpa.dao.UserRepository;
import at.fh.swenga.jpa.dao.UserRoleRepository;
import at.fh.swenga.jpa.model.StudentModel;


@Controller
public class StudentController {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	InstituteRepository instituteRepository;

	@Autowired
	DietRepository dietRepository;

	@Autowired
	DormRepository dormRepository;
	
	@Autowired	
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	PositionRepository positionRepository;

	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
		List<StudentModel> students = studentRepository.findAll();
		model.addAttribute("students", students);
		model.addAttribute("count", students.size());
		return "index";
	}

	@RequestMapping(value = { "/getPage" })
	public String getPage(Pageable page, Model model) {
		Page<StudentModel> studentsPage = studentRepository.findAll(page);
		model.addAttribute("studentsPage", studentsPage);
		model.addAttribute("students", studentsPage.getContent());
		model.addAttribute("count", studentsPage.getTotalElements());
		return "index";
	}
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    //The date format to parse or output your dates
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    //Create a new CustomDateEditor
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    //Register it as custom editor for the Date type
	    binder.registerCustomEditor(Date.class, editor);
	}

	
	@RequestMapping(value = {"/index"}, method = RequestMethod.GET) public String
	handleIndex() { 
		  return "index"; 
	}
	 
	
	@RequestMapping(value = {"/aboutUs"}, method = RequestMethod.GET)
	public String handleAboutUs() {
		return "aboutUs";
	}
	

	@RequestMapping(value = {"/blank"}, method = RequestMethod.GET)
	public String handleBlank() {
		return "blank";
	}
	
	@RequestMapping(value = {"/charts"}, method = RequestMethod.GET)
	public String handleCharts() {
		return "charts";
	}
	
	@RequestMapping(value = {"/allUsers"}, method = RequestMethod.GET)
	public String handleAllUsers() {
		return "allUsers";
	}
	
	@RequestMapping(value = {"/addEvent"}, method = RequestMethod.GET)
	public String handleAddEvent() {
		return "addEvent";
	}
	
	@RequestMapping(value = {"/settings"}, method = RequestMethod.GET)
	public String handleSettings() {
		return "settings";
	}
	
	@RequestMapping(value = {"/supportMail"}, method = RequestMethod.GET)
	public String handleSupportMail() {
		return "supportMail";
	}
	
	@RequestMapping(value = {"/forgotPassword"}, method = RequestMethod.GET)
	public String handleForgotPassword() {
		return "forgotPassword";
	}
	
	@RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
	public String handleProfile() {
		return "profile";
	}
	
	@RequestMapping(value = {"/eventInfo"}, method = RequestMethod.GET)
	public String handleEventInfo() {
		return "eventInfo";
	}
	
	@RequestMapping(value = {"/eventsAttending"}, method = RequestMethod.GET)
	public String handleEventsAttending() {
		return "eventsAttending";
	}
	
	@RequestMapping(value = {"/eventsOwn"}, method = RequestMethod.GET)
	public String handleEventsOwn() {
		return "eventsOwn";
	}

	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	public String handleSearch(Model model) {
		
		List<StudentModel> students = studentRepository.findAll();
		model.addAttribute("students", students);
		
		return "search";
	}
	
	@RequestMapping(value= {"/delete"})
	public String deleteData(Model model, @RequestParam int id) {
		studentRepository.deleteById(id);

		return "forward:list";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "404";

	}
	
	
}
