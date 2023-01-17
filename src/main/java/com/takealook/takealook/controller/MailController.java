package com.takealook.takealook.controller;

import com.takealook.takealook.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MailController {

    private final MailService mailService;

//    @PostMapping("/mail")
//    public void mail (@RequestBody MailRequestDto mailDto) throws MessagingException {
//        mailService.answerMail(mailDto);
//    }
    @PostMapping("/mail")
    public void mail() {
        mailService.sendMail();
    }
}
