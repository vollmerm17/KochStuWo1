package at.fh.swenga.jpa.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
import at.fh.swenga.jpa.model.DietModel;
import at.fh.swenga.jpa.model.DocumentModel;
import at.fh.swenga.jpa.model.DormModel;
import at.fh.swenga.jpa.model.InstituteModel;
import at.fh.swenga.jpa.model.StudentModel;

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
	UserRoleRepository userRoleRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	PositionRepository positionRepository;

	@Autowired
	DocumentRepository  documentRepository;


	/* eigener Controller f�r Request Mappings? */

	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
		List<StudentModel> students = studentRepository.findAll();
		model.addAttribute("students", students);
		model.addAttribute("count", students.size());
		return "index";
	}

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

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String handleIndex() {
		return "index";
	}

	@RequestMapping(value = { "/aboutUs" }, method = RequestMethod.GET)
	public String handleAboutUs() {
		return "aboutUs";
	}

	@RequestMapping(value = { "/blank" }, method = RequestMethod.GET)
	public String handleBlank() {
		return "blank";
	}

	@RequestMapping(value = {"/charts"}, method = RequestMethod.GET)
	public String handleCharts() {
		return "charts";
	}

	@RequestMapping(value = {"/allUsers"}, method = RequestMethod.GET)
	public String handleAllUsers() {
		return "allUsers";
	}

	@RequestMapping(value = {"/addEvent"}, method = RequestMethod.GET)
	public String handleAddEvent() {
		return "addEvent";
	}

	@RequestMapping(value = {"/settings"}, method = RequestMethod.GET)
	public String handleSettings() {
		return "settings";
	}

	@RequestMapping(value = {"/supportMail"}, method = RequestMethod.GET)
	public String handleSupportMail() {
		return "supportMail";
	}

	@RequestMapping(value = {"/forgotPassword"}, method = RequestMethod.GET)
	public String handleForgotPassword() {
		return "forgotPassword";
	}

	@RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
	public String handleProfile() {
		return "profile";
	}

	@RequestMapping(value = {"/eventInfo"}, method = RequestMethod.GET)
	public String handleEventInfo() {
		return "eventInfo";
	}

	@RequestMapping(value = {"/eventsAttending"}, method = RequestMethod.GET)
	public String handleEventsAttending() {
		return "eventsAttending";
	}

	@RequestMapping(value = {"/eventsOwn"}, method = RequestMethod.GET)
	public String handleEventsOwn() {
		return "eventsOwn";
	}

	@RequestMapping(value = {"/uploadPicture"}, method = RequestMethod.GET)
	public String handleUploadPicture() {
		return "uploadPicture";
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String handleSearch(Model model) {

		List<StudentModel> students = studentRepository.findAll();
		model.addAttribute("students", students);

		return "search";
	}

	@PostMapping(value = { "/addEvent" })
	public String addEvent(Model model, @RequestParam String name, @RequestParam String destination,
							@RequestParam Date date, @RequestParam Date time, @RequestParam String description,
							@RequestParam int attendeesMax, StudentModel student) {

		EventModel event1 = new EventModel(name, destination, date, time, description, attendeesMax, student);
		eventRepository.save(event1);



		return "index";
	}

	@PostMapping(value = { "/profile" })
	public String addEvent(Model model, @RequestParam String name, @RequestParam String destination,
							@RequestParam Date date, @RequestParam Date time, @RequestParam String description,
							@RequestParam int attendeesMax, StudentModel student) {

		EventModel event1 = new EventModel(name, destination, date, time, description, attendeesMax, student);
		eventRepository.save(event1);



		return "index";
	}

	@RequestMapping(value = { "/delete" })
	public String deleteData(Model model, @RequestParam int id) {
		studentRepository.deleteById(id);

		return "forward:list";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadForm(Model model, @RequestParam("id") int studentId) {
		model.addAttribute("studentId", studentId);
		return "uploadPicture";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadDocument(Model model, @RequestParam("id") int studentId,
			@RequestParam("myPicture") MultipartFile file) {

		try {

			Optional<StudentModel> studentOpt = studentRepository.findById(studentId);
			if (!studentOpt.isPresent()) throw new IllegalArgumentException("No student with id "+studentId);

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


	@RequestMapping("/download")
	public void download(@RequestParam("documentId") int documentId, HttpServletResponse response) {

		Optional<DocumentModel> docOpt = documentRepository.findById(documentId);
		if (!docOpt.isPresent()) throw new IllegalArgumentException("No document with id "+documentId);

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

		return "error";

	}


}
