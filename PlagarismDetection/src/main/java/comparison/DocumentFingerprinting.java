package comparison;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Checks plagiarism using fingerprinting
 */
public class DocumentFingerprinting implements ComparisonTechniques {

    private static Logger logger = LogManager.getLogger();

    // Rabin and Karp constants
    private static final int K = 5;
    private static final int P = 101;
    private static final double DOUBLE_CONVERT = 0.0;


    // constants for winnowing
    private static final int WINDOW_SIZE = 4;

    /**
     * Get the merged document called mainAfterMerge for that student in given homework.
     *
     * @param studentName student whose document is to be found.
     * @return Document the combined document.
     */
    private StringBuilder getMergedDocument(String homework, String studentName) {
        String filename = homework + studentName + "/mainAfterMerge.py";
        StringBuilder doc = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();

            while (line != null) {
                doc.append(line);
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            logger.error("cannot find file" + e);
        } catch (IOException e) {
            logger.error("exception in reading the file" + e);
        }

        return doc;
    }


    /**
     * gets all the hashes of that given document.
     *
     * @param document whose hashes is to generated.
     * @return List of hashes.
     */
    private List<Long> generateHashOfNGrams(StringBuilder document) {
        String doc = document.toString();

        // remove all the blank spaces.
        doc = doc.replace(" ", "");
        List<Long> hashes = new ArrayList();

        List<String> nGrams = new ArrayList();
        for (int i = 0; i < doc.length() - K + 1; i++) {
            nGrams.add(doc.substring(i, i + K));
        }

        // generates hashes using Rabin and Karp.
        long hash = 0;
        for (int i = 0; i < nGrams.size(); i++) {
            String ngram = nGrams.get(i);
            double kpower = Math.pow(P, K - 1.0);
            if (i == 0) {
                for (int j = 0; j < ngram.length(); j++) {
                    int ascii = (int) ngram.charAt(j);
                    hash += ascii * Math.pow(P, j);
                }
            } else {
                String earlierNgram = nGrams.get(i - 1);
                int first = (int) earlierNgram.charAt(0);
                hash = (long) (((hash - first - DOUBLE_CONVERT) / P) + (ngram.charAt(K - 1) * kpower));
            }

            hashes.add(hash);
        }

        return hashes;
    }


    /**
     * Generates fingerprint of that document from hashes of the document
     * Using winnowing.
     *
     * @param hashes hashes used for fingerprinting
     * @return List representing fingerprint
     */
    private List<Long> getFingerPrint(List<Long> hashes) {
        Map<Integer, Long> fingerPrint = new HashMap<>();

        for (int i = 0; i < hashes.size() - WINDOW_SIZE + 1; i++) {
            long min = Long.MAX_VALUE;
            int pos = -1;
            for (int j = i; j < i + WINDOW_SIZE; j++) {
                if (hashes.get(j) <= min) {
                    min = hashes.get(j);
                    pos = j;
                }
            }

            if (!fingerPrint.containsKey(pos)) {
                fingerPrint.put(pos, min);
            }
        }

        List<Long> fingerprintList = new ArrayList<>();


        for (Map.Entry<Integer, Long> entry : fingerPrint.entrySet()) {
            fingerprintList.add(entry.getValue());
        }

        return fingerprintList;
    }

    /**
     * Calculates the percentage of similarity in two fingerprints.
     *
     * @param fingerprint1 fingerprint of document1
     * @param fingerprint2 fingerprint of document2
     * @return returns percentage similarity
     */
    private double getPercentSimilarity(List<Long> fingerprint1, List<Long> fingerprint2) {
        List<Long> intersection = new ArrayList<>();
        List<Long> union = new ArrayList<>();

        for (int i = 0; i < fingerprint1.size(); i++) {
            Long hash = fingerprint1.get(i);
            if (!fingerprint1.isEmpty() && fingerprint2.contains(hash)) {
                fingerprint2.remove(hash);
                intersection.add(hash);
                union.add(hash);
            } else {
                union.add(hash);
            }
        }

        if (!fingerprint2.isEmpty()) {
            union.addAll(fingerprint2);
        }

        double value = ((double) intersection.size() * 100) / (double) union.size();
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(value));
    }


    /**
     * Returns percentage similarities between submissions of two students.
     *
     * @param homeworkPath homework for which results is to be generated.
     * @param student1     name of first student directory
     * @param student2     name of second student directory.
     * @return double Percentage similarity between two students
     */
    @Override
    public double getResult(String homeworkPath, String student1, String student2) {
        StringBuilder doc1 = this.getMergedDocument(homeworkPath, student1);
        StringBuilder doc2 = this.getMergedDocument(homeworkPath, student2);
        List<Long> hash1 = generateHashOfNGrams(doc1);
        List<Long> hash2 = generateHashOfNGrams(doc2);
        List<Long> fingerPrint1 = getFingerPrint(hash1);
        List<Long> fingerPrint2 = getFingerPrint(hash2);

        return getPercentSimilarity(fingerPrint1, fingerPrint2);
    }

    /**
     * gets snippets after the result is generated.
     * @return List with code of student one as first value and rest of the values
     * are the lines in student two's code that are similar to student one.
     */
    @Override
    public List<List<String>> getAllSnippets() {
        return new LinkedList<>();
    }
}
