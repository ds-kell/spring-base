package vn.com.dsk.demo.base.application.services;

import vn.com.dsk.demo.base.adapter.dto.request.SignupRequest;
import vn.com.dsk.demo.base.domain.entities.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);

    void sendMailVerify(SignupRequest signupRequest);

    void sendVerifyCode(SignupRequest signupRequest, String OTP);
}
