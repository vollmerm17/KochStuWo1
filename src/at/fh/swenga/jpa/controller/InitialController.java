package at.fh.swenga.jpa.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import at.fh.swenga.jpa.dao.DietRepository;
import at.fh.swenga.jpa.dao.DormRepository;
import at.fh.swenga.jpa.dao.EventRepository;
import at.fh.swenga.jpa.dao.InstituteRepository;
import at.fh.swenga.jpa.dao.PositionRepository;
import at.fh.swenga.jpa.dao.StudentRepository;
import at.fh.swenga.jpa.dao.UserDao;
import at.fh.swenga.jpa.dao.UserRoleDao;
import at.fh.swenga.jpa.model.DietModel;
import at.fh.swenga.jpa.model.DormModel;
import at.fh.swenga.jpa.model.InstituteModel;
import at.fh.swenga.jpa.model.StudentModel;
import at.fh.swenga.jpa.model.User;
import at.fh.swenga.jpa.model.UserRole;

@Controller
public class InitialController {
	
	@Autowired
	UserDao userDao;

	@Autowired
	UserRoleDao userRoleDao;

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
	PositionRepository positionRepository;
	
	@RequestMapping("/initPage")
	@Transactional
	public String fillData(Model model) {

		UserRole adminRole = userRoleDao.getRole("ROLE_ADMIN");
		if (adminRole == null)
			adminRole = new UserRole("ROLE_ADMIN");

		UserRole userRole = userRoleDao.getRole("ROLE_USER");
		if (userRole == null)
			userRole = new UserRole("ROLE_USER");
	

		DormModel dorm1 = new DormModel("Greenbox", "tfug", "gcjszhdb");
		dormRepository.save(dorm1);

		DietModel diet1 = new DietModel("vegan", "tierische Produkte");
		dietRepository.save(diet1);
		
		DietModel diet2 = new DietModel("keine", "Allesesser");
		dietRepository.save(diet2);
		
		DietModel diet3 = new DietModel("vegetarisch", "Ohne Feisch und Fisch");
		dietRepository.save(diet3);
		
		DietModel diet4 = new DietModel("pesketarisch", "Ohne Feisch aber mit Fisch");
		dietRepository.save(diet4);
		
		DietModel diet5 = new DietModel("latosefrei", "Keine Laktose");
		dietRepository.save(diet5);

		InstituteModel institute1 = new InstituteModel("FH JOANNEUM", "Eckertstraße 30i", " 8020 Graz");
		InstituteModel institute2 = new InstituteModel("Universität Graz", "Sporgasse 5", "8010 Graz");

		instituteRepository.save(institute1);
		instituteRepository.save(institute2);

		Date now = new Date();
//		//		StudentModel student1 = new StudentModel("Claudia", "Vötter", "sd", "sd", "12345", now, "jhds@fhg", "w",institute1, diet1,dorm1,user);
		
		
		User admin = new User("admin", "password", true);
		admin.encryptPassword();
		admin.addUserRole(userRole);
		admin.addUserRole(adminRole);

		StudentModel student3 = new StudentModel("admin", "admin","admin","admin","admin",now,"admin@admin","w",institute1,diet2,dorm1);
		admin.setStudentModel(student3);
		student3.setUser(admin);
		userDao.persist(admin);
		//		studentRepository.save(student3);
		
		User user = new User("claudio", "bububububu", true);
		user.encryptPassword();
		user.addUserRole(userRole);
	
		
		StudentModel student1 = new StudentModel("Claudia", "Vötter", "sd", "sd", "12345", now, "jhds@fhg", "w",institute1, diet1,dorm1);
		userDao.persist(user);
		// studentRepository.save(student1);
		
		
		User user1 = new User("marti", "bububububu", true);
		user1.encryptPassword();
		user1.addUserRole(userRole);

		
		StudentModel student2 = new StudentModel("Martina", "Vollmer", "sd", "sd", "12345", now, "jhds@fhg", "w", institute1, diet1,dorm1);
//		studentRepository.save(student2);
		userDao.persist(user1);
		
		return "forward:initPage";
	}

}
