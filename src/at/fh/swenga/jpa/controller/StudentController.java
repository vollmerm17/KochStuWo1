package at.fh.swenga.jpa.controller;

import java.io.OutputStream;
import java.text.ParseException;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import at.fh.swenga.jpa.dao.DietRepository;
import at.fh.swenga.jpa.dao.DocumentRepository;
import at.fh.swenga.jpa.dao.DormRepository;
import at.fh.swenga.jpa.dao.EventRepository;
import at.fh.swenga.jpa.dao.InstituteRepository;
import at.fh.swenga.jpa.dao.PositionRepository;
import at.fh.swenga.jpa.dao.StudentRepository;
import at.fh.swenga.jpa.dao.UserRepository;
import at.fh.swenga.jpa.model.DietModel;
import at.fh.swenga.jpa.model.DocumentModel;
import at.fh.swenga.jpa.model.DormModel;
import at.fh.swenga.jpa.model.InstituteModel;
import at.fh.swenga.jpa.model.StudentModel;
import at.fh.swenga.jpa.model.UserModel;

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
	PositionRepository positionRepository;

	@Autowired
	DocumentRepository documentRepository;

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

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = { "/allUsers" }, method = RequestMethod.GET)
	public String handleAllUsers(Model model) {

		List<StudentModel> students = studentRepository.findAllWithoutAdmin();
		model.addAttribute("students", students);
		return "allUsers";
	}

	@RequestMapping(value = { "/settings" }, method = RequestMethod.GET)
	public String handleSettings() {
		return "settings";
	}

	@RequestMapping(value = { "/404" }, method = RequestMethod.GET)
	public String handle404() {
		return "404";
	}

	@RequestMapping(value = { "/forgotPassword" }, method = RequestMethod.GET)
	public String handleForgotPassword() {
		return "forgotPassword";
	}

	@RequestMapping(value = { "/profile" }, method = RequestMethod.GET)
	public String handleProfile(Model model, Authentication aut) {

		List<DormModel> dorms = dormRepository.findAll();
		model.addAttribute("dorms", dorms);

		List<DietModel> diets = dietRepository.findAll();
		model.addAttribute("diets", diets);

		List<InstituteModel> institutes = instituteRepository.findAll();
		model.addAttribute("institutes", institutes);

		/*
		 * int studentId =
		 * userRepository.findFirstByUserName(aut.getName()).getStudent().getId();
		 * model.addAttribute("studentId", studentId);
		 */

		return "profile";
	}

	@Transactional
	@PostMapping(value = { "/profile" })
	public String changeProfile(Model model, @Valid UserModel usernew, @Valid StudentModel studentnew,
			Authentication aut, @RequestParam(value = "dormId") int dormId, @RequestParam(value = "dietId") int dietId,
			@RequestParam(value = "instituteId") int instituteId) {

		UserModel user = userRepository.findFirstByUserName(aut.getName());
		StudentModel student1 = studentRepository.findStudentByEmail(user.getStudent().getEmail());

		InstituteModel insti = instituteRepository.getOne(instituteId);
		DormModel dormi = dormRepository.getOne(dormId);
		DietModel dieti = dietRepository.getOne(dietId);

		if (student1 != null) {
			model.addAttribute("errorMessage", "A profile with this E-Mail already exists!<br>");
		}

		else {

			student1 = new StudentModel();
			student1.setEmail(studentnew.getEmail());
			student1.setPhoneNumber(studentnew.getPhoneNumber());
			student1.setDiet(dieti);
			student1.setDorm(dormi);
			student1.setInstitute(insti);
			student1.setCityAndPostalCode(studentnew.getCityAndPostalCode());
			student1.setStreetAndNumber(studentnew.getCityAndPostalCode());
			studentRepository.save(student1);

			return "profile";
		}

		return "profile";
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String handleSearch(Model model) {

		List<StudentModel> students = studentRepository.findAllWithoutAdmin();
		model.addAttribute("students", students);

		return "search";
	}

	@RequestMapping(value = { "/edit" })
	public String editData(Model model, @RequestParam int id) {
		return "profile";
	}

	@RequestMapping(value = { "/delete" })
	public String deleteData(Model model, @RequestParam int id) {
		studentRepository.deleteById(id);

		return "forward:list";
	}

	@RequestMapping(value = "/uploadRecipe", method = RequestMethod.GET)
	public String showUploadFormRecipe(Model model, @RequestParam("id") int studentId) {
		model.addAttribute("studentId", studentId);
		return "uploadRecipe";
	}

	@RequestMapping(value = "/uploadEventPicture", method = RequestMethod.GET)
	public String showUploadFormEventPicture(Model model, @RequestParam("id") int studentId) {
		model.addAttribute("studentId", studentId);
		return "uploadEventPicture";
	}

	@RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.GET)
	public String showUploadFormProfilePicture(Model model, @RequestParam("id") int studentId) {
		model.addAttribute("studentId", studentId);
		return "uploadProfilePicture";
	}

	@RequestMapping(value = "/uploadRecipe", method = RequestMethod.POST)
	public String uploadRecipe(Model model, @RequestParam("id") int studentId,
			@RequestParam("myFile") MultipartFile file) {
		try {

			Optional<StudentModel> studentOpt = studentRepository.findById(studentId);
			if (!studentOpt.isPresent())
				throw new IllegalArgumentException("No student with id " + studentId);

			StudentModel student = studentOpt.get();

			// Already a document available -> delete it
			if (student.getDocument() != null) {
				documentRepository.delete(student.getDocument());
				// Don't forget to remove the relationship too
				student.setDocument(null);
			}

			// Create a new document and set all available infos

			DocumentModel document = new DocumentModel();
			document.setContent(file.getBytes());
			document.setContentType(file.getContentType());
			document.setCreated(new Date());
			document.setFilename(file.getOriginalFilename());
			document.setName(file.getName());
			student.setDocument(document);
			studentRepository.save(student);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}

		return "addEvent";
	}

	@RequestMapping(value = "/uploadEventPicture", method = RequestMethod.POST)
	public String uploadEventPicture(Model model, @RequestParam("id") int studentId,
			@RequestParam("myFile") MultipartFile file) {
		try {

			Optional<StudentModel> studentOpt = studentRepository.findById(studentId);
			if (!studentOpt.isPresent())
				throw new IllegalArgumentException("No student with id " + studentId);

			StudentModel student = studentOpt.get();

			// Already a document available -> delete it
			if (student.getDocument() != null) {
				documentRepository.delete(student.getDocument());
				// Don't forget to remove the relationship too
				student.setDocument(null);
			}

			// Create a new document and set all available infos

			DocumentModel document = new DocumentModel();
			document.setContent(file.getBytes());
			document.setContentType(file.getContentType());
			document.setCreated(new Date());
			document.setFilename(file.getOriginalFilename());
			document.setName(file.getName());
			student.setDocument(document);
			studentRepository.save(student);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}

		return "addEvent";
	}

	@RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.POST)
	public String uploadProfilePicture(Model model, @RequestParam("id") int studentId,
			@RequestParam("myFile") MultipartFile file) {
		try {

			Optional<StudentModel> studentOpt = studentRepository.findById(studentId);
			if (!studentOpt.isPresent())
				throw new IllegalArgumentException("No student with id " + studentId);

			StudentModel student = studentOpt.get();

			// Already a document available -> delete it
			if (student.getDocument() != null) {
				documentRepository.delete(student.getDocument());
				// Don't forget to remove the relationship too
				student.setDocument(null);
			}

			// Create a new document and set all available infos

			DocumentModel document = new DocumentModel();
			document.setContent(file.getBytes());
			document.setContentType(file.getContentType());
			document.setCreated(new Date());
			document.setFilename(file.getOriginalFilename());
			document.setName(file.getName());
			student.setDocument(document);
			studentRepository.save(student);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}

		return "profile";
	}

	@RequestMapping("/download")
	public void download(@RequestParam("documentId") int documentId, HttpServletResponse response) {

		Optional<DocumentModel> docOpt = documentRepository.findById(documentId);
		if (!docOpt.isPresent())
			throw new IllegalArgumentException("No document with id " + documentId);

		DocumentModel doc = docOpt.get();

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

		return "404";

	}
	

}
