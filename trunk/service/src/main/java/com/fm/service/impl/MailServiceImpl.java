package com.fm.service.impl;

import com.fm.service.IMailService;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service(MailServiceImpl.BEAN_NAME)
public class MailServiceImpl implements IMailService
{
   public static final String BEAN_NAME = "mailService";

   @Resource
   private JavaMailSender javaMailSender;

   @Override
   public void testSend()
   {
      SimpleMailMessage message = new SimpleMailMessage();

      message.setTo("p4welo@gmail.com");
      message.setFrom("p4welo@gmail.com");
      message.setSubject("User Registration successful");
      message.setText("The user ");

      javaMailSender.send(message);
   }

   @Override
   public void sendMail(String from, String to, String subject, String body)
   {
      SimpleMailMessage simpleMailMessage = createSimpleMailMessage(from, new String[]{to}, null, null, subject, body);
      javaMailSender.send(simpleMailMessage);
   }

   @Override
   public void sendMail(String from, String to, String cc, String subject, String body)
   {
      SimpleMailMessage simpleMailMessage = createSimpleMailMessage(from, new String[]{to}, new String[]{cc}, null,
              subject, body);
      javaMailSender.send(simpleMailMessage);
   }

   @Override
   public void sendMail(String from, String to[], String[] cc, String replyTo, String subject, String content)
   {
      SimpleMailMessage simpleMailMessage = createSimpleMailMessage(from, to, cc, replyTo, subject, content);
      javaMailSender.send(simpleMailMessage);
   }

   @Override
   public void sendHtmlMail(String from, String to, String subject, String body) throws MessagingException
   {
      MimeMessage mimeMessage = createHtmlMessage(from, new String[]{to}, new String[]{}, null, subject, body);
      javaMailSender.send(mimeMessage);
   }

   @Override
   public void sendHtmlMail(String from, String to, String cc, String subject, String body) throws MessagingException
   {
      MimeMessage mimeMessage = createHtmlMessage(from, new String[]{to}, new String[]{cc}, null, subject, body);
      javaMailSender.send(mimeMessage);
   }

   @Override
   public void sendHtmlMail(String from, String[] to, String[] cc, String replyTo, String subject, String content)
           throws MessagingException
   {
      MimeMessage mimeMessage = createHtmlMessage(from, to, cc, replyTo, subject, content);
      javaMailSender.send(mimeMessage);
   }

   private SimpleMailMessage createSimpleMailMessage(String from, String to[], String[] cc, String replyTo,
                                                     String subject, String content)
   {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(from);
      message.setTo(to);
      message.setCc(cc);
      message.setReplyTo(replyTo);
      message.setFrom(from);
      message.setSubject(subject);
      message.setText(content);
      return message;
   }

   private MimeMessage createHtmlMessage(String from, String to[], String[] cc, String replyTo, String subject,
                                         String content) throws MessagingException
   {
      SimpleMailMessage simpleMailMessage = createSimpleMailMessage(from, to, cc, replyTo, subject, content);
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
      helper.setFrom(simpleMailMessage.getFrom());
      helper.setTo(simpleMailMessage.getTo());
      helper.setCc(simpleMailMessage.getCc());
      helper.setSubject(simpleMailMessage.getSubject());
      helper.setText(simpleMailMessage.getText(), true);
      return message;
   }

   public void setJavaMailSender(JavaMailSender javaMailSender)
   {
      this.javaMailSender = javaMailSender;
   }

}
