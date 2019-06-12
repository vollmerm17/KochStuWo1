package at.fh.swenga.jpa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import at.fh.swenga.jpa.model.UserModel;


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
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String handleLogin() {
		return "login";
	}

	
	@RequestMapping(value= {"/register"})
	@Transactional
	public String register(Model model,@RequestParam String userN,@RequestParam String passwd,@RequestParam String firstN,@RequestParam String lastN, @RequestParam String street,@RequestParam String postal,@RequestParam String tel,@RequestParam Date dob,@RequestParam String mail){
		
		UserModel userin = new UserModel(userN, passwd, true);
		userin.encryptPassword();
		userin.addUserRole(userRoleRepository.findFirstById(2));
		userRepository.save(userin);

		StudentModel student2 = new StudentModel(firstN, lastN, street, postal, tel, dob,
				mail, "w", instituteRepository.findFirstByName("FH JOANNEUM"),
				dietRepository.findFirstByName("vegetarisch"), dormRepository.findFirstByName("Greenbox"));
		userin.setStudent(student2);
		userRepository.save(userin);
		
		return "login";
	}
	

	/*
	 * @RequestMapping(value = { "/find" }) public String find(Model
	 * model, @RequestParam String searchString, @RequestParam String searchType) {
	 * List<StudentModel> students = null; int count = 0;
	 * 
	 * switch (searchType) { case "query1": students = studentRepository.findAll();
	 * break; case "query2": students =
	 * studentRepository.findByLastName(searchString); break; case "query3":
	 * students = studentRepository.findByFirstName(searchString); break; case
	 * "query4": students = studentRepository.findByWhateverName(searchString);
	 * break; case "query5": students = studentRepository.doANameSearchWithLike("%"
	 * + searchString + "%"); break; case "query6": count =
	 * studentRepository.countByLastName(searchString); break; case "query7":
	 * students = studentRepository.removeByLastName(searchString); break; case
	 * "query8": count = studentRepository.deleteByInstituteName(searchString);
	 * break; case "query9": students =
	 * studentRepository.findByLastNameContainingOrFirstNameContainingAllIgnoreCase(
	 * searchString, searchString); break; case "query10": students =
	 * studentRepository.findByOrderByLastNameAsc(); students =
	 * studentRepository.findAllByOrderByLastNameAsc(); break; case "query11":
	 * students = studentRepository.findTop10ByOrderByLastName(); break;
	 * 
	 * case "query12": students =
	 * studentRepository.findByInstituteNameOrderByLastNameAsc(searchString); break;
	 * case "query13": Calendar nowMinus40 = Calendar.getInstance();
	 * nowMinus40.add(Calendar.YEAR, -40); students =
	 * studentRepository.findByDayOfEnrollmentAfter(nowMinus40); break; case
	 * "query14": Calendar year1980 = Calendar.getInstance(); year1980.set(1980, 0,
	 * 1); Calendar year1985 = Calendar.getInstance(); year1985.set(1985, 11, 31);
	 * students = studentRepository.findByDayOfEnrollmentBetween(year1980,
	 * year1985); break; case "query15": students =
	 * studentRepository.findByInstituteName(searchString); break;
	 * 
	 * default: students = studentRepository.findAll(); }
	 * 
	 * model.addAttribute("students", students);
	 * 
	 * if (students != null) { model.addAttribute("count", students.size()); } else
	 * { model.addAttribute("count", count); } return "index"; }
	 */

	@RequestMapping(value = { "/findById" })
	public String findById(@RequestParam("id") StudentModel e, Model model) {
		if (e != null) {
			List<StudentModel> students = new ArrayList<StudentModel>();
			students.add(e);
			model.addAttribute("students", students);
		}
		return "index";
	}

	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		studentRepository.deleteById(id);

		return "forward:list";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "404";

	}
	
	
}
