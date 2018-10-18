package service.plagiarism;

public enum Strategy {
    LCS,
    FINGERPRINT,
    EDIT_DISTANCE,
    ALL;

    /**
     * Given a string representation converts it into a enum
     * @param comparisonStrategy: Comparision strategy in string format
     * @return enum of type Comparision strategy
     */
    public static Strategy toStrategyType(String comparisonStrategy) {

        if (comparisonStrategy.equals("LCS"))
            return Strategy.LCS;
        else if (comparisonStrategy.equals("FINGERPRINT"))
            return Strategy.FINGERPRINT;
        else if (comparisonStrategy.equals("EDIT_DISTANCE")) {
            return Strategy.EDIT_DISTANCE;
        } else {
            return Strategy.ALL;
        }
    }
}
