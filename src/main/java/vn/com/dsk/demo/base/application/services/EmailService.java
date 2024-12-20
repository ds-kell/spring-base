package vn.com.dsk.demo.base.application.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.adapter.dto.request.RegisterInfo;
import vn.com.dsk.demo.base.domain.entities.EmailDetails;
import vn.com.dsk.demo.base.security.jwt.JwtUtils;

import java.io.File;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderAddress;

    private final JwtUtils jwtUtils;

    public void sendMailVerify(RegisterInfo registerInfo) {
        String senderName = "DFS Company";
        String subject = "DSF - Verify your email";
        String content = "Dear [[name]],<br>"
                + "Click the link below to verify your email.<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you.<br>";

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);

        try {
            helper.setFrom(senderAddress, senderName);
            helper.setTo(registerInfo.getEmail());
            helper.setSubject(subject);
            content = content.replace("[[name]]", registerInfo.getUsername());
            content = content.replace("[[URL]]", "https://www.facebook.com/ds.kell.0502/");
            helper.setText(content, true);

            javaMailSender.send(mailMessage);
        }
        catch (Exception e){
            log.error("Invalid input: Please enter a valid integer: ", e);
        }
    }

    public void sendVerifyCode(RegisterInfo registerInfo, String OTP) {
        String senderName = "DFS Company";
        String subject = "DSF - Verify your email";

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);

        String content = "Dear " + registerInfo.getUsername()
                + "\nYour verify code:\n"
                + OTP
                + "\nThank you.";

        try {
            helper.setFrom(senderAddress, senderName);
            helper.setTo(registerInfo.getEmail());
            helper.setSubject(subject);
            helper.setText(content);

            javaMailSender.send(mailMessage);
        }
        catch (Exception e){
            log.error("Invalid input: Please enter a valid integer: ", e);
        }
    }

    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderAddress);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail sent successfully";
        } catch (Exception e) {
            return "Error while sending mail";
        }
    }

    public String sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(senderAddress);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

            javaMailSender.send(mimeMessage);
            return "Mail sent successfully";
        } catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }
}
