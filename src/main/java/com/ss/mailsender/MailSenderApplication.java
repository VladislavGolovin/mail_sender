package com.ss.mailsender;

import com.ss.mailsender.libs.XmlConfig;
import com.ss.mailsender.libs.cache.ExceptionUpdateCached;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;

@SpringBootApplication
@EnableSwagger2
public class MailSenderApplication {

    Logger logger = LoggerFactory.getLogger(MailSenderApplication.class);

    public static void main(String[] args) {
        // checking application config
        ConfigValidator cv = new ConfigValidator();
        int configStatus = cv.configValidator();

        // status code = 0 - config valid, else invalid
        if (configStatus == 0) {
            SpringApplication.run(MailSenderApplication.class, args);
        }
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        final String configFilePath = "some_config.xml";
        XmlConfig xmlConf = new XmlConfig(configFilePath);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        try {
            mailSender.setHost(xmlConf.getField("mail server"));
            try {
                mailSender.setPort(Integer.parseInt(xmlConf.getField("mail port")));
            } catch (NumberFormatException e) {
                String errMsg = "can't read mail port: can't convert String to Integer in config file: ";
                logger.error(errMsg + xmlConf.getFileAbsolutePath(), e);
                throw new RuntimeException();
            }
            mailSender.setUsername(xmlConf.getField("mail user"));
            mailSender.setPassword(xmlConf.getField("mail password"));

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", xmlConf.getField("mail transport protocol"));
            props.put("mail.smtp.auth", xmlConf.getField("mail smtp auth"));
            props.put("mail.smtp.starttls.enable", xmlConf.getField("mail smtp starttls enable"));
            props.put("mail.debug", xmlConf.getField("mail debug"));

        } catch (ExceptionUpdateCached e) {
            String errMsg = "error during reading email settings from config file: ";
            logger.error(errMsg + xmlConf.getFileAbsolutePath(), e);
            throw new RuntimeException();
        }

        return mailSender;
    }
}
