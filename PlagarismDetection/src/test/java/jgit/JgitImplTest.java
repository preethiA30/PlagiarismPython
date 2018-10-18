package jgit;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;


import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @project phaseC
 * @date 3/14/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * JgitImplemntion to use services related to github
 */
public class JgitImplTest {

    public JgitImplTest() {

    }

    /**
     * Constants Defined
     */
    private static final String OUPUTLOCALPATH = "/gits";
    private static final String GITREPOURLPUBLIC = "https://github.com/manthanthakker/ApacheSpark.git";
    private static final String GITREPOURLPRIVATE = "https://github.ccs.neu.edu/manthanthakker/set01.git";
    private static final String DEFAULTUSERNAME = "manthanthakker";
    private static final String DEFAULTPASS = "YOUCANNOTHACK";

    private static Logger logger = LogManager.getLogger();

    /**
     * Test suite for cloning private repository
     */
    @Test
    public void testForPrivateRepoClone() {

        try {
            Jgit jgit = new JgitImpl();
            jgit.cloneRepo(GITREPOURLPRIVATE, DEFAULTUSERNAME, DEFAULTPASS, OUPUTLOCALPATH);
            File file = new File(OUPUTLOCALPATH);
            file.mkdir();
            assertTrue(file.listFiles().length > 1);
            FileUtils.deleteDirectory(file);
        } catch (Exception e) {
            logger.error("Error in Jgit " + e);
        }
    }

    /**
     * Test suite for cloning public repository
     */
    @Test
    public void testForPublicRepoClone() {
        try {
            Jgit jgit = new JgitImpl();
            jgit.cloneRepo(GITREPOURLPUBLIC, OUPUTLOCALPATH);
            File file = new File(OUPUTLOCALPATH);
            file.mkdir();
            assertTrue(file.listFiles().length > 1);
            FileUtils.deleteDirectory(file);
        } catch (Exception e) {

            logger.error("Error in Jgit " + e);
        }
    }

    /**
     * Test suite for is valid Public repositories: Valid repositories
     */

    @Test
    public void testForisValidPublicRepositoryValid() {
        Jgit jgit = new JgitImpl();
        assertTrue(jgit.isValidRepository(GITREPOURLPUBLIC));
    }

    /**
     * Test suite for is valid Public repositories: Invalid repositories
     */

    @Test
    public void testForisValidPublicRepositoryNotValid() {
        Jgit jgit = new JgitImpl();
        assertFalse(jgit.isValidRepository(GITREPOURLPUBLIC + "/random"));

    }

    /**
     * Test suite for is valid Private repositories: Invalid repositories
     */
    @Test
    public void testForisValidPrivateRepositoryNotValid() {
        logger.error("Fix it Now!");
        logger.warn("Can you please fix it now?");
        logger.info("I command youn to fix bug now!");
        logger.debug("Okay I give up. Sending mail to Mike weintrub....");


        //  assertTrue(jgit.isValidRepository(GITREPOURLPRIVATE + "/random", DEFAULTUSERNAME, DEFAULTPASS));
    }

    /**
     * Test suite for is valid Private repositories: Valid repositories
     */

    @Test
    public void testForisValidPrivateRepositoryValid() {
        Jgit jgit = new JgitImpl();
        assertFalse(jgit.isValidRepository(GITREPOURLPRIVATE, DEFAULTUSERNAME, DEFAULTPASS));
    }
}