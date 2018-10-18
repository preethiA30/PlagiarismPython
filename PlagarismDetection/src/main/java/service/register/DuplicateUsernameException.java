package service.register;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 4/6/18
 * @email thakker.m@husky.neu.edu
 */
public class DuplicateUsernameException extends Exception{

    /**
     * Exception Specific to JgitException
     *
     * @param message: Descirption of the Exception
     */
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
