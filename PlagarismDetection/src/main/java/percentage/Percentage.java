package percentage;

/**
 * percentage class is used to store the contents to be included in the plagiarism report
 */
public class Percentage {

    /**
     * Similarity percentage in the 2 files
     */
    private float similarityPercent;
    /**
     * List of matched snippets in the two files
     */

    public Percentage(float similarityPercent){
        this.similarityPercent = similarityPercent;
    }
    public double getPercentage() {
        return similarityPercent;
    }
}