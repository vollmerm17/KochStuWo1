package at.fh.swenga.jpa.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.StudentRepository;
import at.fh.swenga.jpa.dao.UserRepository;

@Controller
public class ReportController {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = { "/supportMail" }, method = RequestMethod.GET)
	private String supportMail() {

		return "supportMail";
	}

	@PostMapping(value = { "/supportMail" })
	public static void main(String[] args,Model model, @RequestParam(value="content") String content, Authentication aut) {
		String to = "KochStuWo@office.com";
		String from = aut.getName();
		final String username = "9b07b6064e8684";
		final String password = "f42b1653c9128b";

		String host = "smtp.mailtrap.io";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "2525");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field
			message.setFrom(new InternetAddress(from));

			// Set To: header field
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("KochStuWo");

			// Put the content of your message
			message.setText(content);

			// Send message
			Transport.send(message);

			model.addAttribute("message", "Your message was sent!");


		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "404";
	}

}
