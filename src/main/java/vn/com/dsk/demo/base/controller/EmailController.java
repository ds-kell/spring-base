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
@RequestMapping("/api/public/")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("mail/send-mail")
    public ResponseEntity<Response> sendMail(@RequestBody EmailDetails details) {
        return ResponseUtils.ok(emailService.sendSimpleMail(details));
    }

    @PostMapping("mail/send-mail-attachment")
    public ResponseEntity<Response> sendMailWithAttachment(@RequestBody EmailDetails details) {
        return ResponseUtils.ok(emailService.sendMailWithAttachment(details));
    }
}
