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
import at.fh.swenga.jpa.dao.UserRepository;
import at.fh.swenga.jpa.dao.UserRoleRepository;
import at.fh.swenga.jpa.model.StudentModel;
import at.fh.swenga.jpa.model.UserModel;
import at.fh.swenga.jpa.model.UserRoleModel;

@Controller
public class SecurityController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	StudentRepository studentRepo;

	@PostMapping("/register")
	public String registerUser(Model model, @Valid UserModel newUser, @Valid StudentModel newStudent, BindingResult bindingResult) {

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
	//		UserModel user = userRepository.findByUsername(@RequestParam String searchString );
			else if (userRepository.findFirstById(newUser.getId()) != null) {
				model.addAttribute("errorMessage", "UserModel already exists!");
			} else {
				UserRoleModel userRoleModel = userRoleRepository.findFirstByRole("ROLE_USER");
				if (userRoleModel == null)
					userRoleModel = new UserRoleModel("ROLE_USER");

				UserModel userModel = new UserModel("user", "password", true);
				userModel.encryptPassword();
				userModel.addUserRole(userRoleModel);
				userRepository.save(userModel);

			}


			return "forward:index";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "404";

	}
}
