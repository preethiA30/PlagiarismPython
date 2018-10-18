package jgit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;


/**
 * @project phaseC
 * @date 3/14/18
 * @email thakker.m@husky.neu.edu
 */
public class JgitImpl implements Jgit {

    private static Logger logger = LogManager.getLogger();

    private static final String EMPTYURL = "";
    private static final String EXCEPTION = "Exception in Cloning  Public repository";


    /**
     * Default Constructor
     */
    public JgitImpl() {
        // Default constructor for beans
    }

    /**
     * This method clones all the files on the localpath inside the github Repository
     *
     * @param repo:         The github url of a public repository
     * @param localPath:The local path of the server where the git files are supposed to be cloned
     * @return The local path of the server where the git files are supposed to be cloned
     */
    @Override
    public String cloneRepo(String repo, String localPath) throws JgitException {

        try {
            // validating input
            if (repo.trim().equals(EMPTYURL))
                throw new JgitException("Please specify repoUrl");
            if (localPath.trim().equals(EMPTYURL))
                throw new JgitException("Please specify LocalPath");

            Git.cloneRepository()
                    .setURI(repo)
                    .setDirectory(new File(localPath))
                    .call();

        } catch (Exception e) {

            logger.error(EXCEPTION + e);
            throw new JgitException(EXCEPTION + e);

        }
        return localPath;
    }

    /**
     * This method clones all the files inside the github Repo
     *
     * @param repo:      The github url of a private repository
     * @param username:  The userName of the github
     * @param password:  Password for the github
     * @param localPath: The local path of the server where the git files are supposed to be cloned
     * @return The local path of the server where the git files are supposed to be cloned
     */
    @Override
    public String cloneRepo(String repo, String username, String password, String localPath) throws JgitException {
        // throws Exception if the repo url is not properly formatted
        try {

            // validating input
            if (repo.trim().equals(EMPTYURL))
                throw new JgitException("Please specify repoUrl");
            if (localPath.trim().equals(EMPTYURL))
                throw new JgitException("Please specify LocalPath");
            if (username.trim().equals(EMPTYURL))
                throw new JgitException("Please specify Valid Username");
            if (password.trim().equals(EMPTYURL))
                throw new JgitException("Please secify password");

            UsernamePasswordCredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);

            Git.cloneRepository()
                    .setURI(repo)
                    .setDirectory(new File(localPath))
                    .setCredentialsProvider(credentialsProvider)
                    .call();

        } catch (Exception e) {

            logger.error("Exception in Cloning Private repository" + e);
            throw new JgitException(EXCEPTION + e);

        }
        return localPath;
    }

    /**
     * This method verfies if the repository url is a valid url or not and is public or not
     *
     * @param repository : url for the repository
     * @return true if the repository is public and exists
     */
    @Override
    public boolean isValidRepository(String repository) {
        try {
            final LsRemoteCommand lsCmd = new LsRemoteCommand(null);
            return !lsCmd.setRemote(repository).call().isEmpty() ;
        } catch (Exception e) {

            logger.error("Error in is valid repository" + e);
            return false;
        }
    }

    /**
     * This method verifies if the repository url is a valid url or not and is accessible by
     * username and password combination or not
     *
     * @param repository : url for the repository
     * @param userName   :   Username for the github  account
     * @param password   :   password for hte github account
     * @return true if the repository is public and exists
     */
    @Override
    public boolean isValidRepository(String repository, String userName, String password) {

        try {
            UsernamePasswordCredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(userName, password);
            final LsRemoteCommand lsCmd = new LsRemoteCommand(null);
            return (lsCmd.setCredentialsProvider(credentialsProvider).setRemote(repository).call().size()) > 0;
        } catch (Exception e) {
            return false;
        }

    }
}
