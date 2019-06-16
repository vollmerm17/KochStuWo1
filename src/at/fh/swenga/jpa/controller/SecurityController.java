package at.fh.swenga.jpa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.fh.swenga.jpa.dao.DietRepository;
import at.fh.swenga.jpa.dao.DormRepository;
import at.fh.swenga.jpa.dao.EventRepository;
import at.fh.swenga.jpa.dao.InstituteRepository;
import at.fh.swenga.jpa.dao.PositionRepository;
import at.fh.swenga.jpa.dao.StudentRepository;
import at.fh.swenga.jpa.dao.UserRepository;
import at.fh.swenga.jpa.dao.UserRoleRepository;
import at.fh.swenga.jpa.model.DietModel;
import at.fh.swenga.jpa.model.DormModel;
import at.fh.swenga.jpa.model.InstituteModel;
import at.fh.swenga.jpa.model.StudentModel;
import at.fh.swenga.jpa.model.UserModel;
import at.fh.swenga.jpa.model.UserRoleModel;

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
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	PositionRepository positionRepository;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String handleLogin() {
		return "login";
	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.GET)
	public String handleRegister() {
		return "register";
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

	@PostMapping("/register")
	public String register (@Valid UserModel usernew,@Valid InstituteModel institute,@Valid StudentModel studentnew,@Valid DietModel diet,@Valid DormModel dorm, BindingResult bindingResult,
			Model model) throws ParseException {

		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid: " + fieldError.getCode() + "<br>";
			}
			
			model.addAttribute("errorMessage", errorMessage);
			return "register";
		}

		UserModel user = userRepository.findFirstByUserName(usernew.getUserName());
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

			user.encryptPassword();
			user.addUserRole(userRoleRepository.findFirstById(2));
			userRepository.save(user);
			
			InstituteModel insti = instituteRepository.findFirstByName(institute.getName());
			DormModel dormi = dormRepository.findFirstByName(dorm.getName());
			DietModel dieti = dietRepository.findFirstByName(diet.getName());
			
			
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
			student.setUser(user);

			System.out.println(institute);
			
			user.setStudent(student);
			userRepository.save(user);
			
			model.addAttribute("message", "New user " + user.getUserName() + "added.");
		}
		return "forward:login";
	}



	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "404";

	}
}
