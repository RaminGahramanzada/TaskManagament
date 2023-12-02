package com.example.taskmanagament

import com.example.taskmanagament.service.EmailService
import spock.lang.Specification
import spock.lang.Subject
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

class EmailServiceTest extends Specification {
    @Subject
    EmailService emailService

    def setup() {
        JavaMailSender javaMailSender = Mock(JavaMailSenderImpl)
        emailService = new EmailService(javaMailSender)
    }

    def "sendOtpEmail should send an email with the correct content"() {
        given:
        String toEmail = "test@example.com"
        String otp = "123456"
        SimpleMailMessage sentMessage = new SimpleMailMessage()

        emailService.javaMailSender.send(_ as SimpleMailMessage) >> {
            sentMessage = it
        }

        when:
        emailService.sendOtpEmail(toEmail, otp)

        then:
        1 * emailService.javaMailSender.send(_ as SimpleMailMessage)

        and:
        sentMessage.to[0] == toEmail
        sentMessage.text == "Your OTP is: $otp"
        sentMessage.subject == "Your One-Time Password (OTP)"
    }
}
