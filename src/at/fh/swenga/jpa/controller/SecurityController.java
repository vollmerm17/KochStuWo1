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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.DietRepository;
import at.fh.swenga.jpa.dao.DocumentRepository;
import at.fh.swenga.jpa.dao.DormRepository;
import at.fh.swenga.jpa.dao.EventRepository;
import at.fh.swenga.jpa.dao.InstituteRepository;
import at.fh.swenga.jpa.dao.StudentRepository;
import at.fh.swenga.jpa.dao.UserRepository;
import at.fh.swenga.jpa.dao.UserRoleRepository;
import at.fh.swenga.jpa.model.DietModel;
import at.fh.swenga.jpa.model.DormModel;
import at.fh.swenga.jpa.model.EventModel;
import at.fh.swenga.jpa.model.InstituteModel;
import at.fh.swenga.jpa.model.StudentModel;
import at.fh.swenga.jpa.model.UserModel;

@Controller
public class SecurityController {
	@Autowired
	StudentRepository studentRepository;

	@Autowired
	InstituteRepository instituteRepository;

	@Autowired
	DietRepository dietRepository;

	@Autowired
	DormRepository dormRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	DocumentRepository documentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@InitBinder
	public void initDateBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@GetMapping("/")
	public String root() {
		return "index";
	}

	@GetMapping("/index")
	public String handleIndex(Model model) {

		List<EventModel> events = eventRepository.findAll();
		model.addAttribute("events", events);
		
		return "index";
	}

    @GetMapping(value = "/login")
	public String handleLogin() {
		List<DormModel> test = dormRepository.findAll();
		if (test.size() > 0) {
			return "login";
		} else {
			return "forward:initPage";
		}
	}

	@GetMapping("/register")
	public String handleRegister(Model model) {

		List<DormModel> dorms = dormRepository.findAll();
		model.addAttribute("dorms", dorms);

		List<DietModel> diets = dietRepository.findAll();
		model.addAttribute("diets", diets);

		List<InstituteModel> institutes = instituteRepository.findAll();
		model.addAttribute("institutes", institutes);

		return "register";
	}



	@Transactional
	@PostMapping("/register")
	public String register(@Valid UserModel usernew, BindingResult userResult,
			@Valid StudentModel studentnew,	Model model, @RequestParam(value="dormId") int dormId, @RequestParam(value="dietId") int dietId, @RequestParam(value ="instituteId") int instituteId) throws ParseException {

		if (userResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : userResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid: " + fieldError.getCode() + "<br>";
			}

			model.addAttribute("errorMessage", errorMessage);
		}
		
		UserModel user = userRepository.findUserByUserName(usernew.getUserName());
		StudentModel student = studentRepository.findStudentByEmail(studentnew.getEmail());

		if (user != null) {
			model.addAttribute("errorMessage", "A profile with this username already exists!<br>");

		}
		if (student != null) {
			model.addAttribute("errorMessage", "A profile with this E-Mail already exists!<br>");
		}

		else {

			user = new UserModel();
			user.setUserName(usernew.getUserName());
			user.setPassword(usernew.getPassword());
			user.setEnabled(true);
			System.out.println(user.getPassword());
			user.encryptPassword();
			user.addUserRole(userRoleRepository.findFirstById(2));
			userRepository.save(user);

			InstituteModel insti = instituteRepository.getOne(instituteId);
			DormModel dormi = dormRepository.getOne(dormId);
			DietModel dieti = dietRepository.getOne(dietId);

			student = new StudentModel();
			student.setId(user.getId());
			student.setFirstName(studentnew.getFirstName());
			student.setLastName(studentnew.getLastName());
			student.setStreetAndNumber(studentnew.getStreetAndNumber());
			student.setCityAndPostalCode(studentnew.getCityAndPostalCode());
			student.setPhoneNumber(studentnew.getPhoneNumber());
			student.setDayOfBirth(studentnew.getDayOfBirth());
			student.setEmail(studentnew.getEmail());
			student.setGender(studentnew.getGender());
			student.setInstitute(insti);
			student.setDiet(dieti);
			student.setDorm(dormi);

			user.setStudent(student);
			userRepository.save(user);

			return "login";

		}
		return "login";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "404";

	}
}
