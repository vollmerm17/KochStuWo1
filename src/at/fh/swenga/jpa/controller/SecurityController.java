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
	
	@RequestMapping(value = { "/register" }, method = RequestMethod.GET)
	public String handleRegister() {
		return "register";
	}
	
	

	/*
	 * @RequestMapping(value = { "/register" })
	 * 
	 * @Transactional public String register(Model model, @RequestParam String
	 * userN, @RequestParam String passwd,
	 * 
	 * @RequestParam String firstN, @RequestParam String lastN, @RequestParam String
	 * street,
	 * 
	 * @RequestParam String postal, @RequestParam String tel, @RequestParam Date
	 * dob, @RequestParam String mail) {
	 * 
	 * UserModel userin = new UserModel(userN, passwd, true);
	 * userin.encryptPassword();
	 * userin.addUserRole(userRoleRepository.findFirstById(2));
	 * userRepository.save(userin);
	 * 
	 * StudentModel student2 = new StudentModel(firstN, lastN, street, postal, tel,
	 * dob, mail, "w", instituteRepository.findFirstByName("FH JOANNEUM"),
	 * dietRepository.findFirstByName("vegetarisch"),
	 * dormRepository.findFirstByName("Greenbox")); userin.setStudent(student2);
	 * userRepository.save(userin);
	 * 
	 * return "register"; }
	 */

	/*
	 * @PostMapping("/register") public String registerUser(Model model, @Valid
	 * UserModel newUser, @Valid StudentModel newStudent, BindingResult
	 * bindingResult) {
	 * 
	 * if (bindingResult.hasErrors()) { String errorMessage = ""; for (FieldError
	 * fieldError : bindingResult.getFieldErrors()) { errorMessage +=
	 * fieldError.getField() + " is invalid: " + fieldError.getCode() + "<br>"; }
	 * model.addAttribute("errorMessage", errorMessage); }
	 * 
	 * 
	 * // StudentModel student =
	 * studentRepo.findStudentByEmail(newStudent.getEmail()); if
	 * (studentRepo.findStudentByEmail(newStudent.getEmail()) != null ) {
	 * model.addAttribute("errorMessage",
	 * "A profile with this E-Mail already exists!<br>"); } if
	 * (newUser.getPassword().length() <= 5 ) { model.addAttribute("errorMessage",
	 * "This Password is too short!<br>"); } // UserModel user =
	 * userRepository.findByUsername(@RequestParam String searchString ); else if
	 * (userRepository.findFirstById(newUser.getId()) != null) {
	 * model.addAttribute("errorMessage", "UserModel already exists!"); } else {
	 * UserRoleModel userRoleModel =
	 * userRoleRepository.findFirstByRole("ROLE_USER"); if (userRoleModel == null)
	 * userRoleModel = new UserRoleModel("ROLE_USER");
	 * 
	 * UserModel userModel = new UserModel("user", "password", true);
	 * userModel.encryptPassword(); userModel.addUserRole(userRoleModel);
	 * userRepository.save(userModel);
	 * 
	 * }
	 * 
	 * 
	 * return "forward:index"; }
	 */

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "404";

	}
}
