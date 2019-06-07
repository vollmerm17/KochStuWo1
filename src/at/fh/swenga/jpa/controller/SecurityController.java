package at.fh.swenga.jpa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import at.fh.swenga.jpa.dao.StudentRepository;
import at.fh.swenga.jpa.dao.UserDao;
import at.fh.swenga.jpa.dao.UserRoleDao;
import at.fh.swenga.jpa.model.StudentModel;
import at.fh.swenga.jpa.model.User;
import at.fh.swenga.jpa.model.UserRole;

@Controller
public class SecurityController {

	@Autowired
	UserDao userDao;

	@Autowired
	UserRoleDao userRoleDao;

	@Autowired
	StudentRepository studentRepo;

	@PostMapping("/register")
	public String registerUser(Model model, @Valid User newUser, @Valid StudentModel newStudent, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid: " + fieldError.getCode() + "<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
		}
		
		
//			StudentModel student = studentRepo.findStudentByEmail(newStudent.getEmail());		
			if (studentRepo.findStudentByEmail(newStudent.getEmail()) != null ) {
				model.addAttribute("errorMessage", "A profile with this E-Mail already exists!<br>");
			}
			if (newUser.getPassword().length() <= 5 ) {
				model.addAttribute("errorMessage", "This Password is too short!<br>");
			}
	//		User user = userDao.findByUsername(@RequestParam String searchString );		
			else if (userDao.findByUsername(newUser.getUserName()) != null) {
				model.addAttribute("errorMessage", "User already exists!");
			} else {
				UserRole userRole = userRoleDao.getRole("ROLE_USER");
				if (userRole == null)
					userRole = new UserRole("ROLE_USER");

				User user = new User("user", "password", true);
				user.encryptPassword();
				user.addUserRole(userRole);
				userDao.persist(user);

			}

			
			return "forward:index";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "404";

	}

}
