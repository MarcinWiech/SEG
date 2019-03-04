package seg.java;

public class RedeclarationComputer {

    private double obstacleX;
    private double obstacleY;
    private double obstacleHeight;

    private double tora;
    private double toda;
    private double asda;
    private double lda;
    private double dispTresh;
    /**
     * We decided to have a variable action that can either be 1 or 2
     * 1 represents the action of TAKE OFF AWAY/LANDING OVER
     * 2 represents the action of TAKE OFF TOWARDS/LANDING TOWARDS
     */
    private double action;


    //Constructions


    public RedeclarationComputer(double tora, double toda, double asda, double lda, double dispTresh, double action) throws InvalidActionException {
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.dispTresh = dispTresh;
        if (action == 1 || action == 2) {
            this.action = action;
        } else {
            throw new InvalidActionException("action can either be 1 or 2");
        }

    }


    /**
     * Checks whether we need to recalculate
     *
     * @param obstacleX
     * @param obstacleY
     * @return true = we need to Recalculate, false otherwise
     */
    public Boolean needsRecalculation(double obstacleX, double obstacleY) {
        if (obstacleX > -60 || obstacleX < 60) {
            return true;
        }
        if (obstacleY > -75 || obstacleY < -75) {
            return true;
        }
        return false;
    }

    /* The actual recalculation happens here */
    public void calculate() {
        double tora;
        double toda;
        double asda;
        double lda;

        if (this.getAction() == 1) {


            tora = calculateTORA1(this.getTora(), 300, this.getObstacleX(), this.getDispTresh());
            toda = calculateTODA1(this.getTora(), this.getToda());
            asda = calculateASDA1(this.getTora(), this.getAsda());
            lda = calculateLDA1(this.getLda(), this.getObstacleX(), 60, this.getObstacleHeight());

            resetParameters(tora, toda, asda, lda);
        } else {


            tora = calculateTORA2(this.getObstacleX(), this.getDispTresh(), this.getObstacleHeight(), 60);
            toda = calculateTODA2(this.getTora());
            asda = calculateASDA2(this.getTora());
            lda = calculateLDA2(this.getObstacleX(), 240, 60);

            resetParameters(tora, toda, asda, lda);
        }
    }

    /**
     * TAKE OFF AWAY/ LANDING OVER calculations
     * All calculations for this have a "1" in their name for each method
     */


    /**
     * TORA calculation 1
     * BREAKDOWN : Original TORA - Blast Protection - Distance from Threshold - Displaced Threshold
     * For Take off AWAY/ Landing OVER
     * blastProtection is usually 300 by standard (300-500 depending on the aircraft)
     * RESA is usually 240 and the Strip End is 60
     *
     * @param tora
     * @param coeficient        (blast protection or RESA + Strip End)
     * @param obstacleX
     * @param displacedTreshold
     * @return recalculated TORA
     */
    public double calculateTORA1(double tora, double coeficient, double obstacleX, double displacedTreshold) {
        double rTora;
        rTora = tora - coeficient - obstacleX - displacedTreshold;
        return rTora;
    }

    /**
     * ASDA calculation 1
     * Based on recalculated TORA and the value of the stopway
     * BREAKDOWN : (R) TORA + STOPWAY
     *
     * @param tora
     * @param asda
     * @return recalculated ASDA
     */
    public double calculateASDA1(double tora, double asda) {
        double rAsda;
        rAsda = tora - calculateStopway(tora, asda);
        return rAsda;
    }

    /**
     * TODA calculation 1
     * Based on recalculated TORA and the value of the clearway
     * BREAKDOWN : (R) TORA + CLEARWAY
     *
     * @param tora
     * @param toda
     * @return recalculated TODA
     */
    public double calculateTODA1(double tora, double toda) {
        double rToda;
        rToda = tora - calculateClearway(tora, toda);
        return rToda;
    }

    /**
     * LDA calculation 1
     * Based on original lda, the distance between the object and the threshold, strip end and Slope calculation
     * BREAKDOWN: Original LDA - Slope Calculation - Distance from Threshold - Strip End
     *
     * @param lda
     * @param obstacleX
     * @param stripEnd       usually 60
     * @param obstacleHeight used for the slope calculation (h * 50)
     * @return recalculated LDA
     */
    public double calculateLDA1(double lda, double obstacleX, double stripEnd, double obstacleHeight) {

        double rLDA;
        rLDA = lda - obstacleX - stripEnd - obstacleHeight * 50;
        return rLDA;
    }

    /**
     * TAKE OFF TOWARDS/LANDING TOWARDS
     * All calculations for this have a "2" in the name
     * The Distance from threshold - obstacleX - usually has to be large
     * The threshold must be in the opposide side of the runway
     */


    /**
     * TORA calculation 2
     * BREAKDOWN : Distance from Threshold + Displaced Threshold - Slope Calculation - Strip End
     * Slope Calculation = object height * 50
     * Strip End is usually 60
     *
     * @param obstacleX         -usually has to be large (the threshold is in the opposide side of the runway)
     * @param displacedTreshold if there is one
     * @param obstacleHeight
     * @param stripEnd
     * @return recalculated TORA
     */
    public double calculateTORA2(double obstacleX, double displacedTreshold, double obstacleHeight, double stripEnd) {
        double rTora;
        rTora = obstacleX - displacedTreshold - obstacleHeight * 50 - displacedTreshold;
        return rTora;
    }

    /**
     * ASDA calculation 2
     * Based on recalculated TORA
     * BREAKDOWN : (R) TORA
     *
     * @param tora
     * @return recalculated ASDA
     */
    public double calculateASDA2(double tora) {
        return tora;
    }

    /**
     * TODA calculation 2
     * Based on recalculated TORA
     * BREAKDOWN : (R) TORA
     *
     * @param tora
     * @return recalculated TODA
     */
    public double calculateTODA2(double tora) {
        return tora;
    }

    /**
     * LDA calculation 2
     * Based on original lda, the distance between the object and the threshold, strip end and Slope calculation
     * BREAKDOWN: Distance from Threshold - RESA - Strip End
     *
     * @param obstacleX = Distance from Threshold
     * @param resa      (usually 240)
     * @param stripEnd  (usually 60)
     * @return recalculated LDA
     */
    public double calculateLDA2(double obstacleX, double resa, double stripEnd) {

        double rLDA;
        rLDA = obstacleX - resa - stripEnd;
        return rLDA;
    }


    /**
     * Setter for obstacle
     * Allows to locate the obstacle based on the input given
     *
     * @param obstacleX
     * @param obstacleY
     * @param obstacleHeight
     */
    public void setObstacleDetails(double obstacleX, double obstacleY, double obstacleHeight) {
        this.obstacleX = obstacleX;
        this.obstacleY = obstacleY;
        this.obstacleHeight = obstacleHeight;
    }

    /**
     * Allows to change or update the parameters without having to reinitialise the runway
     *
     * @param tora
     * @param toda
     * @param asda
     * @param lda
     */
    public void resetParameters(double tora, double toda, double asda, double lda) {
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
    }


    /**
     * Calculate the Stopway strip
     * We set the stopway to be by default 0 until stated otherwise
     *
     * @param tora
     * @param asda
     * @return stopway size
     */
    public double calculateStopway(double tora, double asda) {
        double stopway = 0;
        stopway = asda - tora;
        return stopway;
    }

    /**
     * Calculate the Clearway strip
     * We set the clearway to be 0 until stated otherwise
     *
     * @param tora
     * @param toda
     * @return clearway size
     */
    public double calculateClearway(double tora, double toda) {
        double clearway = 0;
        clearway = toda - tora;
        return clearway;
    }


    //Getters

    public double getTora() {
        return tora;
    }

    public double getToda() {
        return toda;
    }

    public double getAsda() {
        return asda;
    }

    public double getLda() {
        return lda;
    }

    public double getDispTresh() {
        return dispTresh;
    }

    public double getObstacleX() {
        return obstacleX;
    }

    public double getObstacleY() {
        return obstacleY;
    }

    public double getObstacleHeight() {
        return obstacleHeight;
    }

    public double getAction() {
        return action;
    }

    public class InvalidActionException extends Throwable {
        public InvalidActionException(String s) {
            System.out.println(s);
        }
    }
}


