package service.plagiarism;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Merges all the python file to one big file called mergeAfterResult.py
 */
public class MergeFiles {

    private static Logger logger = LogManager.getLogger();
    private String homework;
    private String MERGE_FILE_NAME = "mainAfterMerge.py";
    private String LINE_NO_AFTER_MERGE = "lineNoAfterMerge.txt";

    /**
     * Constructor to specify the homework
     *
     * @param homework
     */
    public MergeFiles(String homework) {
        this.homework = homework;
    }

    /**
     * merges all the python file to one file called mergeAfterResult.py
     */
    public void mergeFiles() {
        List<String> students = getStudentDirectoryName();
        Iterator i = students.iterator();
        while (i.hasNext()) {
            Integer lineNumber = 1;
            String studentName = (String) i.next();
            String directoryName = homework + studentName;

            List<File> files = new ArrayList<>();
            listf(directoryName, files);
            PrintWriter pw1=null;
            try (PrintWriter pw = new PrintWriter(
                     homework  + studentName + "/" + MERGE_FILE_NAME)) {
                 pw1= new PrintWriter(
                        homework + studentName + "/" + LINE_NO_AFTER_MERGE);
                for (File file : files) {
                    if (!file.getName().equals(MERGE_FILE_NAME) && !file.getName().equals(LINE_NO_AFTER_MERGE)) {
                        Integer previousLine = lineNumber;
                        lineNumber = readFileAndAppend(file, pw, lineNumber);
                        Integer lastLine = lineNumber - 1;
                        pw1.println(file.getName());
                        pw1.println(previousLine);
                        pw1.println(lastLine);
                        pw1.flush();
                    }
                }
                pw.flush();
            } catch (FileNotFoundException e) {
                logger.error("Exception reading file mainAfterMerge", e);
            }
            finally {
                if(pw1!=null)
                pw1.close();
            }

        }
    }

    /**
     * reads line from file and adds in PrintWriter
     * @param file file to be read
     * @param pw PrintWriter object
     */
    private Integer readFileAndAppend(File file, PrintWriter pw, Integer lineNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
            String line = br.readLine();
            while (line != null){
                pw.println(line);
                lineNumber++;
                line = br.readLine();
            }

        } catch (IOException e) {
            logger.error("Exception reading files in directory", e);
        }
        return lineNumber;
    }

    /**
     * Gets all files from directory similar to composite design pattern.
     *
     * @param directoryName directory to be searched.
     * @param files         list of files in that directory.
     */
    private void listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()&&!file.isHidden()) {
                files.add(file);
            } else if (file.isDirectory()&&!file.isHidden()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }

    /**
     * Gets all the student directory names in the homework
     * @return List of student directory names.
     */
    private List<String> getStudentDirectoryName() {
        List<String> studentDirectoryName = new ArrayList<>();
        File directory = new File( homework);
        File[] fList = directory.listFiles();

        for (File file : fList) {
            if (file.isDirectory()) {
                studentDirectoryName.add(file.getName());
            }
        }

        return studentDirectoryName;

    }
}
