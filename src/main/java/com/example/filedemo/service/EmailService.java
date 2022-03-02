package com.example.filedemo.service;
import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {
	@Value("${spring.mail.username}")
	private String adresseMail;
    private final static String logoPath="src\\main\\resources\\static\\logo\\logo.jpg";
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);

    @Autowired
    JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String username, String password) {
    	MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setFrom(adresseMail);
			helper.setText(buildEmail(username, password), true);
			helper.setSubject("Paramètres de connexion");
			FileSystemResource res = new FileSystemResource(new File(logoPath));
			helper.addInline("logo", res);
			mailSender.send(message);
			LOGGER.info("email sent");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
    private String buildEmail(String username, String pasword) {
		return "<!DOCTYPE html>" + "<html>" +

				"<head>" + " <title></title>"
				+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
				+ " <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />" + " <style type=\"text/css\">"
				+ " @media screen {" + " @font-face {" + "   font-family: 'Lato';" + "font-style: normal;"
				+ " font-weight: 400;"
				+ "  src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');"
				+ " }" +

				" @font-face {" + " font-family: 'Lato';" + "  font-style: normal;" + "  font-weight: 700;"
				+ " src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');"
				+ "}" +

				"  @font-face {" + " font-family: 'Lato';" + "font-style: italic;" + " font-weight: 400;"
				+ " src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');"
				+ "}" +

				" @font-face {" + " font-family: 'Lato';" + " font-style: italic;" + "font-weight: 700;"
				+ " src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');"
				+ " }  }" +

				"  body," + " table," + " td," + " a {  -webkit-text-size-adjust: 100%;"
				+ "  -ms-text-size-adjust: 100%; }" +

				" table," + " td { mso-table-lspace: 0pt; mso-table-rspace: 0pt; }" +

				"img {  -ms-interpolation-mode: bicubic;}" +

				" img {" + " border: 0;" + " height: auto;" + "line-height: 100%;" + " outline: none;"
				+ " text-decoration: none;        }" +

				" table { border-collapse: collapse !important;}" +

				"  body {" + "  height: 100% !important;" + " margin: 0 !important;" + " padding: 0 !important;"
				+ " width: 100% !important;" +
				"}" +

				"a[x-apple-data-detectors] {"
				+ "  color: inherit !important; text-decoration: none !important;   font-size: inherit !important;   font-family: inherit !important;   font-weight: inherit !important; line-height: inherit !important; }"
				+

				" @media screen and (max-width:600px) {" + " h1 {" + "font-size: 32px !important;"
				+ "  line-height: 32px !important; } }" +

				"div[style*=\"margin: 16px 0;\"] {  margin: 0 !important;  }" + " </style>" + "</head>" +

				"<body style=\"background-image: linear-gradient(to right top, #2d3af1, #8ee0f5) !important; margin: 0 !important; padding: 0 !important;\">"
				+ "<div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\">  </div>"
				+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">" +

				" <tr>" + "<td  align=\"center\">"
				+ " <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"
				+ " <tr>" + "  <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>"
				+ "   </tr>" + " </table>" + "  </td>" + " </tr>" + "<tr>"
				+ "<td  align=\"center\" style=\"padding: 0px 10px 0px 10px;\">"
				+ " <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"
				+ "<tr>"
				+ " <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">"
				+ "<img src='cid:logo' width=\"200\" height=\"200\" style=\"display: block; border: 0px;\" />"
				+ " <h1 style=\"font-size: 48px; font-weight: 400; margin: 2;\">Bienvenue!</h1> " + "  </td>" + "</tr>"
				+ " </table>" + " </td>" + " </tr>" + " <tr>"
				+ "<td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">"
				+ " <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"
				+ " <tr>"
				+ " <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">"
				+ " <p style=\"margin: 0;\">Voici les coordonnées de votre compte pour accéder à votre éspace:</p> <br>"
				+ " <p style=\"margin: 0;\">Nom d'utilisateur: <label style=\"color: #639df5\" > <strong>" + username
				+ "</strong></label> </p>"
				+ " <p style=\"margin: 0;\">Mot de passe: <label style=\"color: #639df5\" > <strong>" + pasword
				+ "</strong></label> </p>" +

				" </td>" + " </tr>" +

				"  <tr>"
				+ "  <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">"
				+ " <p style=\"margin: 0;\">Cordialement,<br></p>" + " </td>" + "</tr>" + "  </table>"
				+ " </td>" + " </tr>" + " <tr>"
				+ " <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">"
				+ " <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"
				+ " <tr>"
				+ " <td bgcolor=\"#c9e7ee\" align=\"center\" style=\"padding: 30px 30px 30px 30px; border-radius: 4px 4px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">"
				+ " <h2 style=\"font-size: 20px; font-weight: 400; color: #666666; margin: 0;\">Besoin d'aide? Contactez nous!<br/></h2>"+adresseMail+"<br/>"
				+ "  <p style=\"margin: 0;\"><a  target=\"_blank\" style=\"color: #666666;\"> <strong> <br>"
				+ "</strong></a></p> <br>"
				+ "  </td>" + " </tr>" + "  </table>" + "  </td>" + "</tr>" + "  <tr>" +

				" </tr>" + " </table>" + "</body>" +

				"</html>";
	}
}
