package pl.edu.ug.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.edu.ug.model.User;

public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage simpleMailMessage;

//    @Autowired
//    private PasswordGenerator passwordGenerator;

    private SimpleMailMessage mailMessage;

    private final String HELLO = "Hello ";
    private final String WELCOME = ". Welcome to PHOTOGRAPHER service!!!";
    private final String SUBJECT_WELCOME = "Welcome to PHOTOGRAPHER service!!!";
    private final String SUBJECT_CHANGE_PASSWD = "RESET PASSWORD";
    private final String CHANGE_PASSWORD = "Your password has been changed";
    private final String REMINDER = "Do not forget to change password";

    public void sendRegisterConformation(String email, String username) {

        mailMessage = new SimpleMailMessage(simpleMailMessage);
        mailMessage.setTo(email);
        mailMessage.setSubject(SUBJECT_WELCOME);
        mailMessage.setText(HELLO + username + WELCOME);

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }

    }

    public void sendTemporaryPasswd(String email, String username , String generatedPasswd) {

        //String generatedPasswd = passwordGenerator.generatePasswd();
        mailMessage = new SimpleMailMessage(simpleMailMessage);
        mailMessage.setTo(email);
        mailMessage.setSubject(SUBJECT_CHANGE_PASSWD);
        mailMessage.setText(HELLO + username + CHANGE_PASSWORD + "\n" + generatedPasswd + "\n" + REMINDER);

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }

    }

    public void sendNoticeOrderCreated(User user){
        //TODO: Mail o utworzeniu zamówienia
        //if po roli?
    }

    public void sendNoticeStatusChange(User user, int status){
        //TODO: Mail o zmianie statusu zamówienia
        //ify po roli i statusie?
    }
}
