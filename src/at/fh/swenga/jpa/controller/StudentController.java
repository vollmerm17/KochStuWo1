package at.fh.swenga.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
	
	

	
	  @RequestMapping(value = {"/index"}, method = RequestMethod.GET) public String
	  handleIndex() { return "index"; }
	 
	
	@RequestMapping(value = {"/aboutUs"}, method = RequestMethod.GET)
	public String handleAboutUs() {
		return "aboutUs";
	}
	

	
	@RequestMapping(value = {"/addEvent"}, method = RequestMethod.GET)
	public String handleAddEvent() {
		return "addEvent";
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
