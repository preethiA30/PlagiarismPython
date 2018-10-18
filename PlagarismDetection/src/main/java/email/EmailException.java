package email;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/19/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * Custom Exception for Email Api.
 */
public class EmailException extends Exception {

    /**
     * Exception Specific to Email
     *
     * @param message: Descirption of the Exception
     */
    public EmailException(String message) {
        super(message);
    }

}
