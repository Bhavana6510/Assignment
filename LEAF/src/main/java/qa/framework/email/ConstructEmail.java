package qa.framework.email;

import javax.mail.Multipart;

/**
 * 
 * @author 10650956
 *
 */
public interface ConstructEmail {

	ConstructEmail config(String host, int port, String from , String encodedPwd);
	ConstructEmail from(String from);
	ConstructEmail tos(String tos);
	ConstructEmail subject(String subject);
	ConstructEmail body(Multipart multipart, String body);
	ConstructEmail attachement(Multipart multipart, String filePath);
	ConstructEmail sendMail(Multipart multipart);
	
}
