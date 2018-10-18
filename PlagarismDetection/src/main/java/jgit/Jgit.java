package jgit;

/**
 * @project phaseC
 * @date 3/14/18
 * @email thakker.m@husky.neu.edu
 */
public interface Jgit {

    /**
     * This method clones all the files on the localpath inside the github Repository
     *
     * @param repo:         The github url of a public repository
     * @param localPath:The local path of the server where the git files are supposed to be cloned
     * @return The local path of the server where the git files are supposed to be cloned
     */
    String cloneRepo(String repo, String localPath) throws JgitException;


    /**
     * This method clones all the files inside the github Repo
     *
     * @param repo:      The github url of a private repository
     * @param username:  The userName of the github
     * @param password:  Password for the github
     * @param localPath: The local path of the server where the git files are supposed to be cloned
     * @return The local path of the server where the git files are supposed to be cloned
     */
    String cloneRepo(String repo, String localPath, String username, String password) throws JgitException;

    /**
     * This method verfies if the repository url is a valid url or not and is public or not
     *
     * @param repository: url for the repository
     * @return true if the repository is public and exists
     */
    boolean isValidRepository(String repository);

    /**
     * This method verifies if the repository url is a valid url or not and is accessible by
     * username and password combination or not
     *
     * @param repository: url for the repository
     * @param userName:   Username for the github  account
     * @param password:   password for hte github account
     * @return true if the repository is public and exists
     */
    boolean isValidRepository(String repository, String userName, String password);
}
