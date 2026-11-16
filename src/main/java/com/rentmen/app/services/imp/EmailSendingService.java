package com.rentmen.app.services.imp;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.rentmen.app.DTO.JobDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class EmailSendingService {

	private final GraphServiceClient<?> graphClient;
	private final String mailbox;

	public EmailSendingService(
			@Value("${microsoft.client-id}") String clientId,
			@Value("${microsoft.client-secret}") String clientSecret,
			@Value("${microsoft.tenant-id}") String tenantId,
			@Value("${microsoft.mailbox}") String mailbox
	) {
		this.mailbox = mailbox;

		ClientSecretCredential credential = new ClientSecretCredentialBuilder()
				.clientId(clientId)
				.clientSecret(clientSecret)
				.tenantId(tenantId)
				.build();

		TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
				Collections.singletonList("https://graph.microsoft.com/.default"),
				credential
		);

		this.graphClient = GraphServiceClient
				.builder()
				.authenticationProvider(authProvider)
				.buildClient();
	}

	public String sendEmail(String from, String subject, String to, Long jobId, Long userId, JobDto updatedJob) {
		to = "sam.ghori9@gmail.com";
		Message message = new Message();
		message.subject = subject;

		// Construct HTML body
		String htmlMsg = "<p>Beste gebruiker,</p>"
				+ "<p>U heeft een werkverzoek ontvangen.</p>"
				+ "<p>Datum/Tijd: " + updatedJob.getStartDate() + " " + updatedJob.getStartTime() + "</p>"
				+ "<p><a href=\"https://eventicobe.watisdeplanning.nl/api/job/acceptJobOfferFromEmail?jobId=" + jobId + "&userId=" + userId + "\">"
				+ "<button style=\"background-color: green; color: white; padding: 10px 15px; border: none; cursor: pointer;\">Accepteren</button>"
				+ "</a></p>"
				+ "<p>Log alstublieft in om te accepteren.</p>"
				+ "<p>Dank u wel</p>";

		// Set message body
		ItemBody messageBody = new ItemBody();
		messageBody.contentType = BodyType.HTML;
		messageBody.content = htmlMsg;
		message.body = messageBody;

		// To Recipient
		Recipient recipient = new Recipient();
		EmailAddress emailAddress = new EmailAddress();
		emailAddress.address = to;
		recipient.emailAddress = emailAddress;
		message.toRecipients = Collections.singletonList(recipient);

		// ParameterSet for sending
		UserSendMailParameterSet params = UserSendMailParameterSet
				.newBuilder()
				.withMessage(message)
				.withSaveToSentItems(true)
				.build();

		// Send the mail
		graphClient.users(mailbox)
				.sendMail(params)
				.buildRequest()
				.post();

		return "Email Sent Successfully";
	}

	public void sendEmail(String from, String subject, String text, String to) {
		to = "samghori9@gmail.com";

		Message message = new Message();
		message.subject = subject;

		// Body (plain text or basic HTML)
		ItemBody messageBody = new ItemBody();
		messageBody.contentType = BodyType.TEXT; // or BodyType.HTML if `text` contains HTML
		messageBody.content = text;
		message.body = messageBody;

		// To Recipient
		Recipient recipient = new Recipient();
		EmailAddress emailAddress = new EmailAddress();
		emailAddress.address = to;
		recipient.emailAddress = emailAddress;
		message.toRecipients = Collections.singletonList(recipient);

		// Wrap in parameter set
		UserSendMailParameterSet params = UserSendMailParameterSet
				.newBuilder()
				.withMessage(message)
				.withSaveToSentItems(true)
				.build();

		// Send
		graphClient.users(mailbox)
				.sendMail(params)
				.buildRequest()
				.post();
	}

}

//
//import com.rentmen.app.DTO.JobDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//@Service
//public class EmailSendingService {
//	@Autowired
//	private JavaMailSender mailSender;
//
//	public EmailSendingService() {
//	}
//
//	public String sendEmail(String from, String subject, String text, String to) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom(from);
//		message.setSubject(subject);
//		message.setText(text);
//		message.setTo(to);
//		this.mailSender.send(message);
//		return "Email Sent";
//	}
//
//	public String sendEmail(String from, String subject, String to, Long jobId, Long userId, JobDto updatedJob) {
//		MimeMessage message = this.mailSender.createMimeMessage();
//
//		try {
//			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//			helper.setFrom(from);
//			helper.setSubject(subject);
//			String htmlMsg = "<p>Beste gebruiker,</p><p>U heeft een werkverzoek ontvangen.</p><p>Datum/Tijd: " + updatedJob.getStartDate() + " " + updatedJob.getStartTime() + "</p><p><a href=\"https://eventicobe.watisdeplanning.nl/api/job/acceptJobOfferFromEmail?jobId=" + jobId + "&userId=" + userId + "\"><button style=\"background-color: green; color: white; padding: 10px 15px; border: none; cursor: pointer;\">Accepteren</button></a></p><p>Log alstublieft in om te accepteren.</p><p>Dank u wel</p>";
//			helper.setText(htmlMsg, true);
//			helper.setTo(to);
//			this.mailSender.send(message);
//			return "Email Sent";
//		} catch (MessagingException var10) {
//			MessagingException e = var10;
//			e.printStackTrace();
//			return "Error Sending Email";
//		}
//	}
//}
