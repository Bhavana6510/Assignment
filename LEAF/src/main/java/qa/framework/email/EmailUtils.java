package qa.framework.email;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import qa.framework.utils.FileManager;
import qa.framework.utils.LoggerHelper;

public class EmailUtils implements ConstructEmail {

	private static Logger log = LoggerHelper.getLogger(EmailUtils.class);

	private ConstructEmail constructEmail;

	private MimeMessage message;

	/**
	 * Configure email
	 * 
	 * @author 10650956
	 */
	@Override
	public ConstructEmail config(String host, int port, String from, String encodedPwd) {

		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);

		log.info("SMTP host and port are set.");

		String password = FileManager.base64Decode(encodedPwd);

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		log.info("New session created.");

		message = new MimeMessage(session);

		constructEmail = this;

		return constructEmail;
	}

	/**
	 * Set form
	 * 
	 * @author 10650956
	 */
	@Override
	public ConstructEmail from(String from) {

		try {
			message.setFrom(new InternetAddress(from));

		} catch (MessagingException e) {
			e.printStackTrace();
			log.error("Exception: " + e);
		}

		return constructEmail;
	}

	/**
	 * Set semi-comma separater tos
	 * 
	 * @author 10650956
	 */
	@Override
	public ConstructEmail tos(String tos) {

		for (String to : tos.split(";")) {
			try {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				log.info("To set as: " + to);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		return constructEmail;
	}

	/**
	 * Set subject
	 * 
	 * @author 10650956
	 */
	@Override
	public ConstructEmail subject(String subject) {

		try {

			message.setSubject(subject);
			log.info("Subject set as : " + subject);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return constructEmail;
	}

	/**
	 * Set body content
	 * 
	 * @author 10650956
	 */
	@Override
	public ConstructEmail body(Multipart multipart, String body) {

		BodyPart bodyPart = new MimeBodyPart();

		try {
			bodyPart.setText(body);

			/* adding body message */
			multipart.addBodyPart(bodyPart);
			log.info("Body message set.");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return constructEmail;
	}

	/**
	 * Attached file
	 * 
	 * @author 10650956
	 */
	@Override
	public ConstructEmail attachement(Multipart multipart, String multiFilePath) {

		try {
			String[] filePaths = multiFilePath.split(";");

			for (String filePath : filePaths) {

				/* create new MimeBodyPart object and set DataHandler object to this object */
				MimeBodyPart mimeBodyPart = new MimeBodyPart();

				DataSource source = new FileDataSource(filePath);
				String fileName = source.getName();

				mimeBodyPart.setDataHandler(new DataHandler(source));
				mimeBodyPart.setFileName(fileName);

				multipart.addBodyPart(mimeBodyPart);

				log.info("File attached: " + fileName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return constructEmail;
	}

	/**
	 * Send mail
	 * 
	 * @author 10650956
	 */
	@Override
	public ConstructEmail sendMail(Multipart multipart) {

		try {

			if (multipart.getCount() > 0) {
				message.setContent(multipart);
			}

			Transport.send(message);
			log.info("Message Sent...");

		} catch (MessagingException e) {

		}

		return constructEmail;
	}

}
