package pl.edu.ug.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.edu.ug.model.Order;
import pl.edu.ug.model.User;

public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @Autowired
    private MessageSource messageSource;

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
        mailMessage = new SimpleMailMessage(simpleMailMessage);
        mailMessage.setTo(user.getEmail());
        // subject : Order created
        mailMessage.setSubject(messageSource.getMessage("mail.subject.orderCreated",null, LocaleContextHolder.getLocale()));

        Object[] args = new Object[] { user.getName() };

        if(user.containsRole(User.ROLE_USER))
            mailMessage.setText(messageSource.getMessage("mail.content.client.orderCreated", args, LocaleContextHolder.getLocale()));
        else mailMessage.setText(messageSource.getMessage("mail.content.photographer.orderCreated",args, LocaleContextHolder.getLocale()));

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    public void sendNoticeStatusChange(User user, int status){
        mailMessage = new SimpleMailMessage(simpleMailMessage);
        mailMessage.setTo(user.getEmail());
        // subject : Order status changed
        mailMessage.setSubject(messageSource.getMessage("mail.subject.statusChanged",null, LocaleContextHolder.getLocale()));

        // get status name
        String statusName = null;
        switch(status){
            case 2:
                statusName = messageSource.getMessage("status.modified", null, LocaleContextHolder.getLocale());
                break;
            case 3:
                statusName = messageSource.getMessage("status.accepted", null, LocaleContextHolder.getLocale());
                break;
            case 4:
                statusName = messageSource.getMessage("status.rejected", null, LocaleContextHolder.getLocale());
                break;
        }

        if(status == Order.STATUS_MODIFIED){
            Object[] args = new Object[]{user.getName()};
            if (user.containsRole(User.ROLE_USER))
                mailMessage.setText(messageSource.getMessage("mail.content.client.statusChange.modified", args, LocaleContextHolder.getLocale()));
            else
                mailMessage.setText(messageSource.getMessage("mail.content.photographer.statusChange.modified", args, LocaleContextHolder.getLocale()));
        } else {
            Object[] args = new Object[]{user.getName(), statusName};
            if (user.containsRole(User.ROLE_USER))
                mailMessage.setText(messageSource.getMessage("mail.content.client.statusChange", args, LocaleContextHolder.getLocale()));
            else
                mailMessage.setText(messageSource.getMessage("mail.content.photographer.statusChange", args, LocaleContextHolder.getLocale()));
        }

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
