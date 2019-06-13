package at.fh.swenga.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
import at.fh.swenga.jpa.model.UserModel;
import at.fh.swenga.jpa.model.UserRoleModel;

@Controller
public class SecurityController {

	@Autowired
	StudentRepository studentRepo;
	
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
	
	
	/*
	@RequestMapping(value = { "/register" }, method = RequestMethod.GET)
	public String handleRegister() {
		return "register";
	}
	
	

				UserModel userModel = new UserModel("user", "password", true);
				userModel.encryptPassword();
				userModel.addUserRole(userRoleModel);
				userRepository.save(userModel);

			}


			return "forward:index";
	}

*/
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "404";

	}
}
