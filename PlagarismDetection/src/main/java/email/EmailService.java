package email;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/19/18
 * @email thakker.m@husky.neu.edu
 */
public interface EmailService {


    /**
     * This method sends an email to the emailId from the default smptp server
     *
     * @param emailId: Email Address of the recipient
     * @param header:  Header Text
     * @param body:    Well formatted message
     */
    void sendEmail(String emailId, String header, String body) throws EmailException;
}
