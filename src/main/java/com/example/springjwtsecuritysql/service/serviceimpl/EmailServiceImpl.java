package com.example.springjwtsecuritysql.service.serviceimpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.springjwtsecuritysql.core.Constants;
import com.example.springjwtsecuritysql.reponsitory.UserRepository;
import com.example.springjwtsecuritysql.reponsitory.repoimpl.UserRepositoryImpl;
import com.example.springjwtsecuritysql.service.EmailService;
import com.example.springjwtsecuritysql.service.email.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepositoryImpl userRepository;

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
        } catch (Exception e) {
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
            helper.setTo(to);
            //send an email to multiple recipients
            //helper.setTo(InternetAddress.parse("hoangxuanphong200@gmail.com,a32712@thanglong.edu.vn"));
//            List<String> allEmails = getAllEmail();
//            String[] emails = new String[allEmails.size()];
//            for (int i = 0; i < allEmails.size(); i++) {
//                emails[i] = allEmails.get(i);
//            }
//            helper.setTo(emails);
            helper.setText("<html><body><img src='cid:spitterLogo'>" +
                    "<h4>" + email + " says...</h4>" +
                    "<i>" + email + "</i>" +
                    "</body></html>", true);
            //ClassPathResource image = new ClassPathResource("C:/Users/ADMIN/Downloads/z3723258270826_cb2a4cf5453d322299cc2eb5160d5488.jpg");
            //helper.addInline("spitterLogo", image);
            //helper.addAttachment("z3723258270826_cb2a4cf5453d322299cc2eb5160d5488.jpg", image);
            helper.setSubject("Confirm your email");
            helper.setFrom("trung03trung@gmail.com");
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public List<String> getAllEmail() {
        List<String> listEmails = userRepository.getAllEmail();
        return listEmails;
    }

    public String buildOtpEmails(String name, String otp) {
        return "<p>Xin ch??o " + name + ".Nh???p m?? OTP nh?? d?????i ????y d??? ?????i m???t kh???u </p>"
                + "<br>" + "<h3>" + otp + "</h3>" + "<br>"
                + "<p>M?? OTP n??y s??? h???t h???n trong 5 ph??t</p>";
    }

    public String buildActiveLink(String link) {
        return "Link active account" +
                "<a href=\" " + link + "\">Click v??o ????y ????? k??ch ho???t t??i kho???n</a>";
    }

    public String buildMailInterview(String jobName, String time,
                                     String date, String mediatype,
                                     String jeName, String jePhone,
                                     String userName) {
        return "<p>Dear anh/ch??? " + userName + " <p><br>"
                + "<p>C??ng ty ITSOL r???t vui v?? vinh h???nh khi nh???n ???????c h??? s?? ???ng tuy???n c???a anh/ch??? v??o v??? tr?? "
                + jobName + ". Ch??ng t??i ???? nh???n ???????c CV c???a anh/ch??? v?? mong mu???n c?? m???t cu???c ph???ng v???n ????? trao "
                + "?????i tr???c ti???p v??? ki???n th???c c??ng nh?? c??ng vi???c m?? anh/ch??? ???? ???ng tuy???n.</p><br>"
                + "<p>Th???i gian ph???ng v???n d??? ki???n v??o l??c " + time + " ng??y " + date + " qua c??ng c??? " + mediatype
                + "(ch??ng t??i s??? g???i l???i link sau khi anh/ch??? x??c nh???n ?????ng ?? ph???ng v???n b???ng c??c reply l???i mail n??y).</p><br>"
                + "<p>Ch??ng t??i r???t hy v???ng anh/ch??? s???m ph???n h???i v?? mong r???ng ch??ng ta s??? ???????c h???p t??c c??ng nhau trong t????ng lai.</p><br>"
                + "<p>M???i th???c m???c xin vui l??ng li??n h??? t???i anh " + jeName + ", S??T: " + jePhone + " trong gi??? h??nh ch??nh ????? ???????c gi???i ????p.</p><br>"
                + "<p>Thanks & best regards,</p><br><p>ITSOL JSC</p><br><p>Head office: T???ng 3, t??a nh?? 3A, ng?? 82, ph??? Duy T??n, ph?????ng D???ch V???ng H???u,qu???n C???u Gi???y, H?? N???i</p><br>"
                + "<p>Hotline: 0123456789</p>";
    }
    public String buildActiveEmail(String name, String otp, Long id) {
        String link = "http://localhost:9090/api/auth" + Constants.Api.Path.Account.CHANGE_PASSWORD + "?otp=" + otp + "&id=" + id;
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Ch??o " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> C???m ??n b???n ???? ????ng k?? t??i kho???n. H??y click v??o link d?????i ????y ????? k??ch ho???t t??i kho???n: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    public String buildOtpEmail(String name, String otp) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Ch??o " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> ????y l?? m?? OTP ????? ?????i m???t kh???u </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + otp + " </p></blockquote>\n M?? n??y s??? h???t h???n trong 5 ph??t. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

//    public String buildSchedule(JobRegister jobRegister) {
//        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
//        SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
//        return "<div>\n" +
//                "  <p>Dear anh/ch??? <strong></strong></p>\n" +
//                "  <p>C??ng ty ITSOL r???t vui v?? vinh h???nh khi nh???n ???????c h??? s?? ???ng tuy???n c???a anh/ch??? v??o v??? tr?? " + jobRegister.getJob().getJobPosition().getCode() + ".\n" +
//                "    Ch??ng t??i ???? nh???n ???????c CV c???a anh/ch??? v?? mong mu???n c?? m???t cu???c ph???ng v???n ????? trao ?????i tr???c ti???p v???\n" +
//                "    ki???n th???c c??ng nh?? c??ng vi???c m?? anh/ch??? ???? ???ng tuy???n.\n" +
//                "  </p>\n" +
//                "  <p>\n" +
//                "    Th???i gian ph???ng v???n d??? ki???n v??o l??c " + time.format(jobRegister.getDateInterview()) + " ng??y " + day.format(jobRegister.getDateInterview()) + " qua c??ng c??? " + jobRegister.getAddressInterview() + "\n" +
//                "    (ch??ng t??i s??? g???i l???i link sau khi anh/ch??? x??c nh???n ?????ng ?? ph???ng v???n b???ng c??c reply l???i mail n??y).\n" +
//                "  </p>\n" +
//                "  <p>\n" +
//                "    Ch??ng t??i r???t hy v???ng anh/ch??? s???m ph???n h???i v?? mong r???ng ch??ng ta s??? ???????c h???p t??c c??ng nhau trong t????ng lai.\n" +
//                "  </p>\n" +
//                "  <p>\n" +
//                "    M???i th???c m???c xin vui l??ng li??n h??? t???i " + jobRegister.getUser().getName() + ", S??T: " + jobRegister.getUser().getPhoneNumber() + " trong gi??? h??nh ch??nh ????? ???????c gi???i ????p.\n" +
//                "  </p>\n" +
//                "  <p>\n" +
//                "    Thanks & best regards,\n" +
//                "  </p>\n" +
//                "  <p>\n" +
//                "    ITSOL JSC\n" +
//                "  </p>\n" +
//                "  <p>\n" +
//                "    Head office: T???ng 3, t??a nh?? 3A, ng?? 82, ph??? Duy T??n, ph?????ng D???ch V???ng H???u, qu???n C???u Gi???y, H?? N???i\n" +
//                "  </p>\n" +
//                "  <p>Hotline: 0123456789</p>\n" +
//                "</div>\n";
//    }
}