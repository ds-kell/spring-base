package vn.com.dsk.demo.base.application.usecases;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.adapter.dto.request.RegisterInfo;
import vn.com.dsk.demo.base.application.services.AuthService;
import vn.com.dsk.demo.base.application.services.EmailService;
import vn.com.dsk.demo.base.application.services.RedisService;
import vn.com.dsk.demo.base.infrastructure.exception.ServiceException;

import java.security.SecureRandom;

import static vn.com.dsk.demo.base.shared.constants.Constants.CHARACTERS;
@Slf4j
@Service
public class PreRegisterUseCase implements UseCase<String, RegisterInfo>{

    private final AuthService authService;

    private final RedisService redisService;

    private final EmailService emailService;

    public PreRegisterUseCase(AuthService authService, RedisService redisService, EmailService emailService) {
        this.authService = authService;
        this.emailService= emailService;
        this.redisService = redisService;
    }

    @Override
    public String execute(RegisterInfo registerInfo) {
        // check exist
        if (authService.checkEmailOrUserNameExistInSystem(registerInfo)) {
            throw new ServiceException("Email or username is existed in system!", "err.api.email-username-is-existed");
        }
//        if(!Objects.isNull(redisService.getRegisterInfoByToken(registerInfo.getEmail()))){
//            throw new ServiceException("You have requested too many times, please try again later.", "err.api.email-requested-too-many");
//        }
        // sendOTP
        String OTP = generateOTP();
        try {
            redisService.saveToken(OTP + registerInfo.getUsername(), registerInfo, 300);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "RedisService is not responding";
        }
        try {
            emailService.sendVerifyCode(registerInfo, OTP);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "The server is busy and cannot send email. Please try again later.";
        }
        return "Check your email";
    }

    private String generateOTP() {
        StringBuilder otp = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            otp.append(randomChar);
        }
        return otp.toString();
    }
}