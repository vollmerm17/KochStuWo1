package at.fh.swenga.jpa.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import at.fh.swenga.jpa.dao.DietRepository;
import at.fh.swenga.jpa.dao.DormRepository;
import at.fh.swenga.jpa.dao.EventRepository;
import at.fh.swenga.jpa.dao.InstituteRepository;
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



	@RequestMapping(value = {"/initPage"})
	public String fillData(Model model) {

		this.createUserRoles();

		this.createDorms();

		this.createDiets();

		this.createInstitutes();

		this.createUsersAndStudent();

		this.createEvents();


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
		DormModel dorm1 = new DormModel("Greenbox", "Eggenberger Allee 31", "8020 Graz");
		dormRepository.save(dorm1);

		DormModel dorm2 = new DormModel("JAB-Haus", "Glacisstrasse 39", "8010 Graz");

		dormRepository.save(dorm2);
		DormModel dorm3 = new DormModel("home4students", "Leechgasse 1", "8010 Graz");
		dormRepository.save(dorm3);

	}



	public void createDiets() {
		DietModel diet1 = new DietModel("vegan", "Tierische Produkte");
		dietRepository.save(diet1);

		DietModel diet2 = new DietModel("keine", "Allesesser");
		dietRepository.save(diet2);

		DietModel diet3 = new DietModel("vegetarisch", "Ohne Fleisch und Fisch");
		dietRepository.save(diet3);

		DietModel diet4 = new DietModel("pescetarisch", "Ohne Fleisch aber mit Fisch");
		dietRepository.save(diet4);

		DietModel diet5 = new DietModel("laktosefrei", "Keine Laktose");
		dietRepository.save(diet5);

	}

	public void createInstitutes() {
		InstituteModel institute1 = new InstituteModel("FH JOANNEUM", "Eckertstrasse 30i", " 8020 Graz");
		instituteRepository.save(institute1);

		InstituteModel institute2 = new InstituteModel("Universitaet Graz", "Sporgasse 5", "8010 Graz");
		instituteRepository.save(institute2);


		InstituteModel institute3 = new InstituteModel("Technische Universitaet Graz", "Rechbauerstrasse 12", "8010 Graz");

		instituteRepository.save(institute2);

	}

	Date now = new Date();
	public void createUsersAndStudent() {


		List<UserModel> sortedUserList = userRepository.findAllId();
		if (sortedUserList.isEmpty()) {

			UserModel admin = new UserModel("administrator", "password", true);

			admin.encryptPassword();
			admin.addUserRole(userRoleRepository.findFirstById(1));
			admin.addUserRole(userRoleRepository.findFirstById(2));
			userRepository.save(admin);

			StudentModel student3 = new StudentModel(admin.getUserId(),"admin", "admin", "admin", "admin", "admin", now,
					"admin@admin", "f", instituteRepository.findFirstByInstituteName("FH JOANNEUM"),
					dietRepository.findFirstByDietName("vegan"), dormRepository.findFirstByDormName("Greenbox"));
			admin.setStudent(student3);
			userRepository.save(admin);

			UserModel user = new UserModel("Maxi", "geheim2345", true);
			user.encryptPassword();
			user.addUserRole(userRoleRepository.findFirstById(2));
			userRepository.save(user);

			StudentModel student1 = new StudentModel(user.getUserId(),"Maximillian", "Mustermann", "sd", "sd", "12345", now,
					"jhds@fhg", "m", instituteRepository.findFirstByInstituteName("FH JOANNEUM"),
					dietRepository.findFirstByDietName("keine"), dormRepository.findFirstByDormName("Greenbox"));
			user.setStudent(student1);
			userRepository.save(user);

			UserModel userin = new UserModel("Maxine", "geheim2345", true);
			userin.encryptPassword();
			userin.addUserRole(userRoleRepository.findFirstById(2));
			userRepository.save(userin);

			StudentModel student2 = new StudentModel(userin.getUserId(),"Maxine", "Mustermann", "sd", "sd", "12345", now,
					"jhds@fhg", "f", instituteRepository.findFirstByInstituteName("FH JOANNEUM"),
					dietRepository.findFirstByDietName("vegetarisch"), dormRepository.findFirstByDormName("Greenbox"));

			userin.setStudent(student2);

			userRepository.save(userin);

		} else {

			;
		}


	}

	public void createEvents(){

		List<EventModel> sortedEventList = eventRepository.findAll();
		if (sortedEventList.isEmpty()) {

		UserModel maxi = userRepository.findFirstByUserName("Maxi");
		UserModel maxine = userRepository.findFirstByUserName("Maxine");
		DormModel dorm1 = dormRepository.findFirstByDormName("Greenbox");
		DormModel dorm2 = dormRepository.findFirstByDormName("home4students");
		DietModel diet1 = dietRepository.findFirstByDietName("vegan");
		DietModel diet2 = dietRepository.findFirstByDietName("keine");


			EventModel event1 = new EventModel("Salami Party","Ich mag Salami und Brot",now,now,dorm2,diet1,3,maxi);
			eventRepository.save(event1);
			EventModel event2 = new EventModel("Pizza Party","Ich mag Pizza mit Sardellen und Mais",now,now,dorm1,diet2,3,maxine);
			eventRepository.save(event2);
			EventModel event3 = new EventModel("Late Night Dinner","Ich hoffe wir haben einen schoenen Abend",now,now,dorm1,diet2,1,maxine);
			eventRepository.save(event3);
			
	}
		else{
			;}
		}



}
