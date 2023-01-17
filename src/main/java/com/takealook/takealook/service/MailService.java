package com.takealook.takealook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail() {
        ArrayList<String> toUserList = new ArrayList<>();
        toUserList.add("dirn0568@naver.com");

        int toUserSize = toUserList.size();

        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));

        simpleMessage.setSubject("Subject Sample");

        simpleMessage.setText("T메세지테스트32");

        javaMailSender.send(simpleMessage);
    }
}
