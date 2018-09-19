package es.jesmon.services.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

//http://javasampleapproach.com/spring-framework/spring-boot/configure-javamailsender-springboot
@Component("javasampleapproachMailSender")
public class MailSender {
	
    @Autowired  
    private JavaMailSender javaMailSender;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 @Value("${spring.mail.from}")
	 private String from;
	
	public void sendMail(String to, String subject, String body) throws MessagingException {
		/*
		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(body);
		
		logger.info("Sending...");
		
		javaMailSender.send(mail);
		
		logger.info("Done!");
		*/
		MimeMessage mail = javaMailSender.createMimeMessage();
		 
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);
		javaMailSender.send(mail);
	}
}