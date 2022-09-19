package com.example.springjwtsecuritysql.service.serviceimpl;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.springjwtsecuritysql.service.EmailService;
import com.example.springjwtsecuritysql.service.email.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    // Method 1
    // To send a simple email
    public String sendSimpleMail(EmailDetails details) {
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    // Method 2
    // To send an email with attachment
    public String
    sendMailWithAttachment(EmailDetails details) {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());
            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }
        // Catch block to handle MessagingException
        catch (MessagingException e) {
            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }

    @Override
    public String sendEmailToMultipleRecipients(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            //helper.setTo(to);
            //send an email to multiple recipients
            //helper.setTo(InternetAddress.parse("hoangxuanphong200@gmail.com,a32712@thanglong.edu.vn"));
            helper.setTo(new String[]{"hoangxuanphong2000@gmail.com","dangthu0211@gmail.com"});
            helper.setSubject("Confirm your email");
            helper.setFrom("trung03trung@gmail.com");
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException("failed to send email");
        }
    }

    public String buildOtpEmail(String name, String otp) {
        return "<p>Xin chào " + name + ".Nhập mã OTP như dưới đây dể đổi mật khẩu </p>"
                + "<br>" + "<h3>" + otp + "</h3>" + "<br>"
                + "<p>Mã OTP này sẽ hết hạn trong 5 phút</p>";
    }

    public String buildActiveLink(String link) {
        return "Link active account" +
                "<a href=\" " + link + "\">Click vào đây để kích hoạt tài khoản</a>";
    }

    public String buildMailInterview(String jobName, String time,
                                     String date, String mediatype,
                                     String jeName, String jePhone,
                                     String userName) {
        return "<p>Dear anh/chị " + userName + " <p><br>"
                + "<p>Công ty ITSOL rất vui và vinh hạnh khi nhận được hồ sơ ứng tuyển của anh/chị vào vị trí "
                + jobName + ". Chúng tôi đã nhận được CV của anh/chị và mong muốn có một cuộc phỏng vấn để trao "
                + "đổi trực tiếp về kiến thức cũng như công việc mà anh/chị đã ứng tuyển.</p><br>"
                + "<p>Thời gian phỏng vấn dự kiến vào lúc " + time + " ngày " + date + " qua công cụ " + mediatype
                + "(chúng tôi sẽ gửi lại link sau khi anh/chị xác nhận đồng ý phỏng vấn bằng các reply lại mail này).</p><br>"
                + "<p>Chúng tôi rất hy vọng anh/chị sớm phản hồi và mong rằng chúng ta sẽ được hợp tác cùng nhau trong tương lai.</p><br>"
                + "<p>Mọi thắc mắc xin vui lòng liên hệ tới anh " + jeName + ", SĐT: " + jePhone + " trong giờ hành chính để được giải đáp.</p><br>"
                + "<p>Thanks & best regards,</p><br><p>ITSOL JSC</p><br><p>Head office: Tầng 3, tòa nhà 3A, ngõ 82, phố Duy Tân, phường Dịch Vọng Hậu,quận Cầu Giấy, Hà Nội</p><br>"
                + "<p>Hotline: 0123456789</p>";
    }
}