package vn.com.dsk.demo.base.service;

import vn.com.dsk.demo.base.dto.request.SignupRequest;
import vn.com.dsk.demo.base.model.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);

    void sendMailVerify(SignupRequest signupRequest);

    void sendVerifyCode(SignupRequest signupRequest);
}
