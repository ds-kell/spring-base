package vn.com.dsk.demo.base.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.dsk.demo.base.model.EmailDetails;
import vn.com.dsk.demo.base.service.EmailService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("public/mail/send-mail")
    public String sendMail(@RequestBody EmailDetails details) {
        return emailService.sendSimpleMail(details);
    }

    @PostMapping("public/mail/send-mail-attachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        return emailService.sendMailWithAttachment(details);
    }
}
