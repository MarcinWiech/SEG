package seg.java;

public class RedeclarationComputer {
    /** Fields **/
    private double obstacleX;
    private double obstacleY;
    private double obstacleHeight;

    private double tora;
    private double toda;
    private double asda;
    private double lda;
    private double threshold;

    /** Constructors **/

    public RedeclarationComputer()
    {

    }

    public RedeclarationComputer(double tora, double toda, double asda, double lda, double  threshold) {
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.threshold = threshold;
    }

    /** Methods **/

    /** Checks whether we need to recalculate */
    public Boolean needsRecalculation()
    {
        return true;
    }

    /** The actual recalculation happens here */
    public void calculate()
    {

    }

    /** Setters **/
    public void setObstacleDetails(double obstacleX, double obstacleY, double obstacleHeight) {
        this.obstacleX = obstacleX;
        this.obstacleY = obstacleY;
        this.obstacleHeight = obstacleHeight;
    }

    public void setParameters(double tora, double toda, double asda, double lda, double  threshold) {
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.threshold = threshold;
    }

    /** Getters **/
    public double getTora() { return tora; }
    public double getToda() { return toda; }
    public double getAsda() { return asda; }
    public double getLda() { return lda;}
    public double getDispTresh() { return threshold; }
}


