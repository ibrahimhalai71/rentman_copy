package com.rentmen.app.services.imp;

import com.rentmen.app.DTO.JobDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSendingService {
	@Autowired
	private JavaMailSender mailSender;

	public EmailSendingService() {
	}

	public String sendEmail(String from, String subject, String text, String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setSubject(subject);
		message.setText(text);
		message.setTo(to);
//		this.mailSender.send(message);
		return "Email Sent";
	}

	public String sendEmail(String from, String subject, String to, Long jobId, Long userId, JobDto updatedJob) {
		MimeMessage message = this.mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(from);
			helper.setSubject(subject);
			String htmlMsg = "<p>Beste gebruiker,</p><p>U heeft een werkverzoek ontvangen.</p><p>Datum/Tijd: " + updatedJob.getStartDate() + " " + updatedJob.getStartTime() + "</p><p><a href=\"https://eventicobe.watisdeplanning.nl/api/job/acceptJobOfferFromEmail?jobId=" + jobId + "&userId=" + userId + "\"><button style=\"background-color: green; color: white; padding: 10px 15px; border: none; cursor: pointer;\">Accepteren</button></a></p><p>Log alstublieft in om te accepteren.</p><p>Dank u wel</p>";
			helper.setText(htmlMsg, true);
			helper.setTo(to);
			//this.mailSender.send(message);
			return "Email Sent";
		} catch (MessagingException var10) {
			MessagingException e = var10;
			e.printStackTrace();
			return "Error Sending Email";
		}
	}
}
