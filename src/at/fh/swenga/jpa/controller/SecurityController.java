package at.fh.swenga.jpa.controller;
 
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
 
	@RequestMapping("/login")
	@Transactional
	public String fillData(Model model) {
 
		UserRole adminRole = userRoleDao.getRole("ROLE_ADMIN");
		if (adminRole == null)
			adminRole = new UserRole("ROLE_ADMIN");
 
		UserRole userRole = userRoleDao.getRole("ROLE_USER");
		if (userRole == null)
			userRole = new UserRole("ROLE_USER");
 
		User admin = new User("admin", "password", true);
		admin.encryptPassword();
		admin.addUserRole(userRole);
		admin.addUserRole(adminRole);
		userDao.persist(admin);
 
		User user = new User("user", "password", true);
		user.encryptPassword();
		user.addUserRole(userRole);
		userDao.persist(user);
 
		return "forward:login";
	}
 
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
 
		return "error";
 
	}
}
