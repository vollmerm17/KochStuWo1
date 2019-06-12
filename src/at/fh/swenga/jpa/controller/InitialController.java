package at.fh.swenga.jpa.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class InitialController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

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
	public String fillData(Model model) {

		this.createUserRoles();

		this.createDorms();

		this.createDiets();

		this.createInstitutes();

		this.createUsersAndStudent();

		return "initPage";
	}
	

	public void createUserRoles() {
		UserRoleModel adminRole = userRoleRepository.findFirstByRole("ROLE_ADMIN");
		if (adminRole == null) {
			adminRole = new UserRoleModel("ROLE_ADMIN");
			userRoleRepository.save(adminRole);
		}

		UserRoleModel userRoleModel = userRoleRepository.findFirstByRole("ROLE_USER");
		if (userRoleModel == null) {
			userRoleModel = new UserRoleModel("ROLE_USER");
			userRoleRepository.save(userRoleModel);
		}

	}

	public void createDorms() {
		DormModel dorm1 = new DormModel("Greenbox", "tfug", "gcjszhdb");
		dormRepository.save(dorm1);

	}

	public void createDiets() {
		DietModel diet1 = new DietModel("vegan", "tierische Produkte");
		dietRepository.save(diet1);

		DietModel diet2 = new DietModel("keine", "Allesesser");
		dietRepository.save(diet2);

		DietModel diet3 = new DietModel("vegetarisch", "Ohne Fleisch und Fisch");
		dietRepository.save(diet3);

		DietModel diet4 = new DietModel("pesketarisch", "Ohne Fleisch aber mit Fisch");
		dietRepository.save(diet4);

		DietModel diet5 = new DietModel("laktosefrei", "Keine Laktose");
		dietRepository.save(diet5);

	}

	public void createInstitutes() {
		InstituteModel institute1 = new InstituteModel("FH JOANNEUM", "Eckertstraﬂe 30i", " 8020 Graz");
		instituteRepository.save(institute1);

		InstituteModel institute2 = new InstituteModel("Universit‰t Graz", "Sporgasse 5", "8010 Graz");
		instituteRepository.save(institute2);

	}

	public int createId(int id) {
		List<UserModel> sortedUserList = userRepository.findAllId();
		for (int i = 0; sortedUserList.size() < i; i++) {
			id = sortedUserList.get(0).getId() + 1;
		}
		return id;

	}

	public void createUsersAndStudent() {

		Date now = new Date();
		List<UserModel> sortedUserList = userRepository.findAllId();
		int id;
		if (sortedUserList.isEmpty()) {
			id = 1;

			UserModel admin = new UserModel(id, "administrator", "password", true);

			admin.encryptPassword();
			admin.addUserRole(userRoleRepository.findFirstById(1));
			admin.addUserRole(userRoleRepository.findFirstById(2));
			userRepository.save(admin);

			StudentModel student3 = new StudentModel(id, "admin", "admin", "admin", "admin", "admin", now,
					"admin@admin", "w", instituteRepository.findFirstByName("FH JOANNEUM"),
					dietRepository.findFirstByName("vegan"), dormRepository.findFirstByName("Greenbox"));
			admin.setStudent(student3);
			userRepository.save(admin);
			
			UserModel userModel = new UserModel(createId(2), "Maxi", "geheim2345", true);
			userModel.encryptPassword();
			userModel.addUserRole(userRoleRepository.findFirstById(2));
			userRepository.save(userModel);

			StudentModel student1 = new StudentModel(createId(2), "Maximillian", "Mustermann", "sd", "sd", "12345", now,
					"jhds@fhg", "m", instituteRepository.findFirstByName("FH JOANNEUM"),
					dietRepository.findFirstByName("keine"), dormRepository.findFirstByName("Greenbox"));
			userModel.setStudent(student1);
			userRepository.save(userModel);
			
			UserModel userin = new UserModel(createId(3), "Maxine", "dasGehtdichNichtsAn", true);
			userin.encryptPassword();
			userin.addUserRole(userRoleRepository.findFirstById(2));
			userRepository.save(userin);

			StudentModel student2 = new StudentModel(createId(3), "Maxine", "Mustermann", "sd", "sd", "12345", now,
					"jhds@fhg", "w", instituteRepository.findFirstByName("FH JOANNEUM"),
					dietRepository.findFirstByName("vegetarisch"), dormRepository.findFirstByName("Greenbox"));
			userin.setStudent(student2);
			userRepository.save(userin);

		} else {

			;
		}

	}

}
