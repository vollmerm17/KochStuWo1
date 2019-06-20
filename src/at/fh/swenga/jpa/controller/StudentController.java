package at.fh.swenga.jpa.controller;


import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.codec.binary.Base64;

import at.fh.swenga.jpa.dao.DietRepository;
import at.fh.swenga.jpa.dao.ProfilePictureRepository;
import at.fh.swenga.jpa.dao.DormRepository;
import at.fh.swenga.jpa.dao.EventPictureRepository;
import at.fh.swenga.jpa.dao.EventRepository;
import at.fh.swenga.jpa.dao.InstituteRepository;
import at.fh.swenga.jpa.dao.StudentRepository;
import at.fh.swenga.jpa.dao.UserRepository;
import at.fh.swenga.jpa.dao.RecipeRepository;
import at.fh.swenga.jpa.model.DietModel;
import at.fh.swenga.jpa.model.ProfilePictureModel;
import at.fh.swenga.jpa.model.DormModel;
import at.fh.swenga.jpa.model.EventModel;
import at.fh.swenga.jpa.model.EventPictureModel;
import at.fh.swenga.jpa.model.InstituteModel;
import at.fh.swenga.jpa.model.StudentModel;
import at.fh.swenga.jpa.model.UserModel;
import at.fh.swenga.jpa.model.RecipeModel;

@Controller
public class StudentController {

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
	EventRepository eventRepository;

	@Autowired
	ProfilePictureRepository profilePictureRepository;
	
	@Autowired
	EventPictureRepository eventPictureRepository;
	
	@Autowired
	RecipeRepository recipeRepository;

	/* eigener Controller fuer Request Mappings? */

	@RequestMapping(value = { "/getPage" })
	public String getPage(Pageable page, Model model) {
		Page<StudentModel> studentsPage = studentRepository.findAll(page);
		model.addAttribute("studentsPage", studentsPage);
		model.addAttribute("students", studentsPage.getContent());
		model.addAttribute("count", studentsPage.getTotalElements());
		return "index";
	}

	@InitBinder
	private void dateBinder(WebDataBinder binder) {
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value = { "/aboutUs" }, method = RequestMethod.GET)
	public String handleAboutUs() {
		return "aboutUs";
	}

	@RequestMapping(value = { "/blank" }, method = RequestMethod.GET)
	public String handleBlank() {
		return "blank";
	}

	@RequestMapping(value = { "/charts" }, method = RequestMethod.GET)
	public String handleCharts() {
		return "charts";
	}

	@RequestMapping(value = { "/allUsers" }, method = RequestMethod.GET)
	public String handleAllUsers(Model model) {

		List<StudentModel> students = studentRepository.findAll();
		model.addAttribute("students", students);
		return "allUsers";
	}

	@RequestMapping(value = { "/settings" }, method = RequestMethod.GET)
	public String handleSettings(Model model, Authentication aut) {
		
		UserModel temp = userRepository.findFirstByUserName(aut.getName());
		model.addAttribute("student", temp.getUserId()) ;
		return "settings";
	}

	@RequestMapping(value = { "/supportMail" }, method = RequestMethod.GET)
	public String handleSupportMail() {
		return "supportMail";
	}

	@RequestMapping(value = { "/forgotPassword" }, method = RequestMethod.GET)
	public String handleForgotPassword() {
		return "forgotPassword";
	}


	@GetMapping("/profile")
	public String handleProfile(Model model, Authentication aut) {
		
		UserModel user = userRepository.findFirstByUserName(aut.getName());
		StudentModel student = studentRepository.findStudentById(user.getUserId());
		
	
		if (student != null) {
			
			model.addAttribute("student", student);
			if (student.getPicture() != null) {
				
				Optional<ProfilePictureModel> ppOpt = profilePictureRepository.findById(student.getPicture().getId());
				ProfilePictureModel pp = ppOpt.get();
				byte[] profilePicture = pp.getContent();

				
				StringBuilder sb = new StringBuilder();
				sb.append("data:image/png;base64,");
				sb.append(Base64.encodeBase64String(profilePicture));
				String image = sb.toString();
				model.addAttribute("image", image);
				
			
			}
			
		

		List<DormModel> dorms = dormRepository.findAll();
		model.addAttribute("dorms", dorms);

		List<DietModel> diets = dietRepository.findAll();
		model.addAttribute("diets", diets);

		List<InstituteModel> institutes = instituteRepository.findAll();
		model.addAttribute("institutes", institutes);
		
		
	}
		return "profile";
	}


	@PostMapping("/profile")
	public String changeProfile(Model model,@RequestParam String userName, @RequestParam String email, DormModel dorm, InstituteModel institute, DietModel diet) {
		UserModel user = userRepository.findFirstByUserName(System.getProperty("user.name"));
		StudentModel student = studentRepository.findStudentByUser(user.getUserId());

		user.setUserName(userName);
		student.setEmail(email);
		student.setDiet(diet);
		student.setDorm(dorm);
		student.setInstitute(institute);



		return "profile";
	}

	/*
	 * @Transactional
	 * 
	 * @PostMapping(value = { "/profile" }) public String changeProfile(Model
	 * model,@Valid StudentModel studentnew, @RequestParam String
	 * userName,Authentication aut, @RequestParam(value="dormId") int
	 * dormId, @RequestParam(value="dietId") int dietId, @RequestParam(value
	 * ="instituteId") int instituteId) { UserModel user =
	 * userRepository.findFirstByUserName(aut.getName()); StudentModel student1 =
	 * studentRepository.findStudentByEmail(user.getStudent().getEmail());
	 * 
	 * InstituteModel insti = instituteRepository.getOne(instituteId); DormModel
	 * dormi = dormRepository.getOne(dormId); DietModel dieti =
	 * dietRepository.getOne(dietId);
	 * 
	 * 
	 * if (user != null) { model.addAttribute("errorMessage",
	 * "A profile with this username already exists!<br>");
	 * 
	 * } if (student1 != null) { model.addAttribute("errorMessage",
	 * "A profile with this E-Mail already exists!<br>"); }
	 * 
	 * else {
	 * 
	 * 
	 * student1 = new StudentModel(); user=new UserModel();
	 * user.setUserName(userName); student1.setEmail(studentnew.getEmail());
	 * student1.setDiet(dieti); student1.setDorm(dormi);
	 * student1.setInstitute(insti);
	 * student1.setCityAndPostalCode(studentnew.getCityAndPostalCode());
	 * student1.setStreetAndNumber(studentnew.getCityAndPostalCode());
	 * student1.setLastName(studentnew.getLastName());
	 * 
	 * studentRepository.merge(student1):
	 * 
	 * return "profile"; } return "profile"; }
	 */

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String handleSearch(Model model) {

		List<StudentModel> students = studentRepository.findAll();
		model.addAttribute("students", students);

		return "search";
	}

	/*
	 * @PostMapping(value = { "/profile" }) public String addEvent(Model
	 * model, @RequestParam String name, @RequestParam String destination,
	 *
	 * @RequestParam Date date, @RequestParam Date time, @RequestParam String
	 * description,
	 *
	 * @RequestParam int attendeesMax, StudentModel student) {
	 *
	 * EventModel event1 = new EventModel(name, destination, date, time,
	 * description, attendeesMax, student); eventRepository.save(event1);
	 *
	 *
	 *
	 * return "index"; }
	 */
	@RequestMapping(value = { "/edit" })
	public String editData(Model model, @RequestParam int id) {
		return "profile";
	}

	@RequestMapping(value = { "/delete" })
	public String deleteData(Model model, @RequestParam int id) {
		studentRepository.deleteById(id);

		return "forward:list";
	}
	
	@RequestMapping(value = { "/deleteOwn"})
	public String deleteOwnData(Model model, Authentication aut) {
	UserModel user = userRepository.findFirstByUserName(aut.getName());
		int currentId =user.getUserId();
		studentRepository.deleteById(currentId);
		userRepository.deleteById(currentId);
		return "login";
	}

	@RequestMapping(value = "/uploadRecipe", method = RequestMethod.GET)
	public String showUploadFormRecipe(Model model, @RequestParam("eventId") int eventId) {
		model.addAttribute("eventId", eventId);
		return "uploadRecipe";
	}


	@RequestMapping(value = "/uploadRecipe", method = RequestMethod.POST)
	public String uploadRecipe1(Model model, @RequestParam("eventId") int eventId,
			@RequestParam("myFile") MultipartFile file) {
		try {
			
			EventModel event = eventRepository.findEventByEventId(eventId);
			

			if (event == null) throw new IllegalArgumentException("No event with id "+eventId);
 
			if (event.getPicture() != null) {
				recipeRepository.delete(event.getRecipe());
				event.setPicture(null);
			}
			
			RecipeModel recipe = new RecipeModel();
			recipe.setContent(file.getBytes());
			recipe.setContentType(file.getContentType());
			recipe.setCreated(new Date());
			recipe.setFilename(file.getOriginalFilename());
			recipe.setRecipeName(file.getName());
			event.setRecipe(recipe);
			recipeRepository.save(recipe);
			eventRepository.save(event);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}

		return "redirect:/index";
	}
	
	
	@RequestMapping(value = "/uploadEventPicture", method = RequestMethod.GET)
	public String showUploadFormEventPicture(Model model, @RequestParam("eventId") int eventId) {
		model.addAttribute("eventId", eventId);
		return "uploadEventPicture";
	}
	
	@RequestMapping(value = "/uploadEventPicture", method = RequestMethod.POST)
	public String uploadEventPicture(Model model, @RequestParam("eventId") int eventId,
			@RequestParam("myFile") MultipartFile file) {
		try {

			
			EventModel event = eventRepository.findEventByEventId(eventId);
			

			if (event == null) throw new IllegalArgumentException("No event with id "+eventId);
 
			
	
			if (event.getPicture() != null) {
				eventPictureRepository.delete(event.getPicture());
				event.setPicture(null);

			}
			if((!"image/png".equals(file.getContentType())) || (!"image/jpeg".equals(file.getContentType()))) {
				model.addAttribute("errorMessage", "Just JPG or PNG Files allowed!");
				return "eventInfo";
			}
			
			EventPictureModel pic = new EventPictureModel();
			pic.setContent(file.getBytes());
			pic.setContentType(file.getContentType());
			pic.setCreated(new Date());
			pic.setFilename(file.getOriginalFilename());
			pic.setName(file.getName());
			event.setPicture(pic);
			eventPictureRepository.save(pic);
			eventRepository.save(event);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}

		return "redirect:/index";
	}

	
	@RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.GET)
	public String showUploadFormProfilePicture(Model model,@RequestParam("id") int studentId) {
		model.addAttribute("studentId", studentId);
		return "uploadProfilePicture";
	}
	
	@RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.POST)
	public String uploadProfilePicture(Model model, @RequestParam("id") int studentId,
			@RequestParam("myFile") MultipartFile file) {
		try {

			
			StudentModel student = studentRepository.findStudentById(studentId);
			if (student == null) throw new IllegalArgumentException("No student with id "+studentId);
 
			
	
			if (student.getPicture() != null) {
				profilePictureRepository.delete(student.getPicture());
				student.setPicture(null);

			}
			
			if((!"image/png".equals(file.getContentType())) || (!"image/jpeg".equals(file.getContentType()))) {
				model.addAttribute("errorMessage", "Just JPG or PNG Files allowed!");
				return "profile";
			}
			
			ProfilePictureModel pic = new ProfilePictureModel();
			pic.setContent(file.getBytes());
			pic.setContentType(file.getContentType());
			pic.setCreated(new Date());
			pic.setFilename(file.getOriginalFilename());
			pic.setName(file.getName());
			student.setPicture(pic);
			profilePictureRepository.save(pic);
			studentRepository.save(student);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}

		return "redirect:/profile";
	}
	
	@RequestMapping("/download")
	public void download(@RequestParam("eventId") int eventId, HttpServletResponse response) {

		
		EventModel event = eventRepository.findEventByEventId(eventId);
		/*if (!event.isPresent())
			throw new IllegalArgumentException("No document with id " + documentId);
*/
		RecipeModel doc = event.getRecipe();
		
		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getFilename() + "\"");
			OutputStream out = response.getOutputStream();
			// application/octet-stream
			response.setContentType(doc.getContentType());
			out.write(doc.getContent());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}

}
