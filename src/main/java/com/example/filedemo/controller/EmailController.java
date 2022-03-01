package com.example.filedemo.controller;


import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.filedemo.payload.Mail;
import com.example.filedemo.service.SendMailService;

@RestController
@RequestMapping("/api/v1/mail/")
public class EmailController {

	SendMailService service;

    public EmailController(SendMailService service) {
        this.service = service;
    }

    //  http://localhost:8080/api/v1/mail/send
    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody Mail mail) {
        service.sendMail(mail);
        return new ResponseEntity<>("Email Sent successfully", HttpStatus.OK);
    }

    // http://localhost:8080/api/v1/mail/attachment
    @PostMapping("/attachment")
    public ResponseEntity<String> sendAttachmentEmail(@RequestBody Mail mail) throws MessagingException {
        service.sendMailWithAttachments(mail);
        return new ResponseEntity<>("Attachment mail sent successfully", HttpStatus.OK);
    }
}
