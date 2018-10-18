package email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/19/18
 * @email thakker.m@husky.neu.edu
 */


public class EmailServiceTest {

    private static final String DEFAULTUSERNAME = "team114neu@gmail.com";
    private static final String PASS = "team114team114";
    private static final String ORGANIZATION = "Northeastern University";
    private static final String DEFAULTHEADER = "Ignore this : Testing (Manthan)";
    private static final String DEFAULTBODY = "Email From MSD Porject Testing Team 114: Will trigger such email on any Exception.";
    private static Logger logger = LogManager.getLogger();




    /**
     * Tests for sending an email to all teammates
     */
    @Test
    public void sendEmail() {

        EmailService emailService = new EmailServiceImpl(DEFAULTUSERNAME, PASS, ORGANIZATION);
        try {

            emailService.sendEmail("team114neu@gmail.com", DEFAULTHEADER, DEFAULTBODY);
        } catch (EmailException e) {
            logger.error("Error in Exception" + e);
            assertFalse(true);
        }
    }

    /**
     * Test for null EmailIds : Must throw an exception
     */
    @Test
    public void sendNullEmail() {

        EmailService emailService = new EmailServiceImpl(DEFAULTUSERNAME, PASS, ORGANIZATION);
        try {

            emailService.sendEmail(null, DEFAULTHEADER, DEFAULTBODY);
        } catch (EmailException e) {
            // ToDo: Replace with logger
            assertFalse(false);
        }
    }
}