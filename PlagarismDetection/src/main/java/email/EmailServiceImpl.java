package email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/19/18
 * @email thakker.m@husky.neu.edu
 */
public class EmailServiceImpl implements EmailService {


    private String companyemail;
    private String password;
    private String organization;
    private static final String HOSTNAME = "smtp.googlemail.com";
    private static final int SMPTPPORT = 465;
    private static Logger logger = LogManager.getLogger();


    public EmailServiceImpl(String username, String password, String organization) {
        this.companyemail = username;
        this.password = password;
        this.organization = organization;
        logger.info("Spring bean called" + username + " " + companyemail + " " + password);
    }

    /**
     * This method sends an email to the emailId from the default smptp server
     *
     * @param emailId : Email Address of the recipient
     * @param header  :  Header Text
     * @param body    :    Well formatted message
     */
    @Override
    public void sendEmail(String emailId, String header, String body) throws EmailException {

        try {

            HtmlEmail email = new HtmlEmail();
            email.setHostName(HOSTNAME);
            email.setSmtpPort(SMPTPPORT);
            email.setAuthenticator(new DefaultAuthenticator(companyemail, password));
            email.setSSLOnConnect(true);
            email.setFrom(companyemail);
            email.setSubject(organization + " " + header);
            email.setHtmlMsg("<html><h1>" + body + "<h1</html>");
            email.addTo(emailId);

            // send the email
            email.send();


        } catch (Exception e) {
            logger.error("Exception in sending an email" + e);
            throw new EmailException("Exception in Sending an email: " + e);
        }
    }
}
