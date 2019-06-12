package at.fh.swenga.jpa.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RequestMapping;

import at.fh.swenga.jpa.dao.UserDao;
import at.fh.swenga.jpa.dao.UserRoleDao;
import at.fh.swenga.jpa.model.User;
import at.fh.swenga.jpa.model.UserRole;
 
@Controller
public class SecurityController {
 
	@Autowired
	UserDao userDao;
 
	@Autowired
	UserRoleDao userRoleDao;
 
	/*
	 * @RequestMapping("/login")
	 * 
	 * @Transactional public String fillData(Model model) {
	 * 
	 * UserRole adminRole = userRoleDao.getRole("ROLE_ADMIN"); if (adminRole ==
	 * null) adminRole = new UserRole("ROLE_ADMIN");
	 * 
	 * UserRole userRole = userRoleDao.getRole("ROLE_USER"); if (userRole == null)
	 * userRole = new UserRole("ROLE_USER");
	 * 
	 * User admin = new User("admin", "password", true); admin.encryptPassword();
	 * admin.addUserRole(userRole); admin.addUserRole(adminRole);
	 * userDao.persist(admin);
	 * 
	 * User user = new User("user", "password", true); user.encryptPassword();
	 * user.addUserRole(userRole); userDao.persist(user);
	 * 
	 * return "forward:login"; }
	 */
 

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
