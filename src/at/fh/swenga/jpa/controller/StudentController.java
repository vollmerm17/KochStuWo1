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
	    //The date format to parse or output your dates
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    //Create a new CustomDateEditor
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    //Register it as custom editor for the Date type
	    binder.registerCustomEditor(Date.class, editor);
	}


	@RequestMapping(value = {"/index"}, method = RequestMethod.GET) public String
	handleIndex() {
		  return "index";
	}


	@RequestMapping(value = {"/aboutUs"}, method = RequestMethod.GET)
	public String handleAboutUs() {
		return "aboutUs";
	}


	@RequestMapping(value = {"/blank"}, method = RequestMethod.GET)
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

	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	public String handleSearch() {
		return "search";
	}

	@RequestMapping(value = {"/settings"}, method = RequestMethod.GET)
	public String handleSettings() {
		return "settings";
	}

	@RequestMapping(value = {"/eventInfo"}, method = RequestMethod.GET)
	public String handleEventInfo() {
		return "eventInfo";
	}

	@RequestMapping(value = {"/eventsOwn"}, method = RequestMethod.GET)
	public String handleEventsOwn() {
		return "eventsOwn";
	}

	@RequestMapping(value = {"/eventsAttending"}, method = RequestMethod.GET)
	public String handleEventsAttending() {
		return "eventsAttending";
	}

	@RequestMapping(value = {"/uploadPicture"}, method = RequestMethod.GET)
	public String handleuploadPicture() {
		return "uploadPicture";
	}




	/*
	 * @RequestMapping(value = { "/find" }) public String find(Model
	 * model, @RequestParam String searchString, @RequestParam String searchType) {
	 * List<StudentModel> students = null; int count = 0;
	 *
	 * switch (searchType) { case "query1": students = studentRepository.findAll();
	 * break; case "query2": students =
	 * studentRepository.findByLastName(searchString); break; case "query3":
	 * students = studentRepository.findByFirstName(searchString); break; case
	 * "query4": students = studentRepository.findByWhateverName(searchString);
	 * break; case "query5": students = studentRepository.doANameSearchWithLike("%"
	 * + searchString + "%"); break; case "query6": count =
	 * studentRepository.countByLastName(searchString); break; case "query7":
	 * students = studentRepository.removeByLastName(searchString); break; case
	 * "query8": count = studentRepository.deleteByInstituteName(searchString);
	 * break; case "query9": students =
	 * studentRepository.findByLastNameContainingOrFirstNameContainingAllIgnoreCase(
	 * searchString, searchString); break; case "query10": students =
	 * studentRepository.findByOrderByLastNameAsc(); students =
	 * studentRepository.findAllByOrderByLastNameAsc(); break; case "query11":
	 * students = studentRepository.findTop10ByOrderByLastName(); break;
	 *
	 * case "query12": students =
	 * studentRepository.findByInstituteNameOrderByLastNameAsc(searchString); break;
	 * case "query13": Calendar nowMinus40 = Calendar.getInstance();
	 * nowMinus40.add(Calendar.YEAR, -40); students =
	 * studentRepository.findByDayOfEnrollmentAfter(nowMinus40); break; case
	 * "query14": Calendar year1980 = Calendar.getInstance(); year1980.set(1980, 0,
	 * 1); Calendar year1985 = Calendar.getInstance(); year1985.set(1985, 11, 31);
	 * students = studentRepository.findByDayOfEnrollmentBetween(year1980,
	 * year1985); break; case "query15": students =
	 * studentRepository.findByInstituteName(searchString); break;
	 *
	 * default: students = studentRepository.findAll(); }
	 *
	 * model.addAttribute("students", students);
	 *
	 * if (students != null) { model.addAttribute("count", students.size()); } else
	 * { model.addAttribute("count", count); } return "index"; }
	 */

	@RequestMapping(value = { "/findById" })
	public String findById(@RequestParam("id") StudentModel e, Model model) {
		if (e != null) {
			List<StudentModel> students = new ArrayList<StudentModel>();
			students.add(e);
			model.addAttribute("students", students);
		}
		return "index";
	}

	@RequestMapping("/fillStudentList")

	@Transactional
	public String fillData(Model model) {


		DormModel dorm1 = new DormModel("Greenbox", "tfug", "gcjszhdb");
		dormRepository.save(dorm1);

		DietModel diet1 = new DietModel("vegan", "tierische Produkte");
		dietRepository.save(diet1);

		InstituteModel institute1 = new InstituteModel("FH JOANNEUM", "Eckertstra�e 30i", " 8020 Graz");
		InstituteModel institute2 = new InstituteModel("Universit�t Graz", "Sporgasse 5", "8010 Graz");

		instituteRepository.save(institute1);
		instituteRepository.save(institute2);

		Date now = new Date();



		return "forward:list";
	}

	@RequestMapping("/delete")
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
