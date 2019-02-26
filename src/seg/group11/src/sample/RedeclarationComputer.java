package sample;

public class RedeclarationComputer
{
    // Fields #################################################################################################################################

    private double obstacleX;
    private double obstacleY;
    private double obstacleHeight;

    private double tora;
    private double toda;
    private double asda;
    private double lda;
    private double dispTresh;

    // Constructors #################################################################################################################################

    public RedeclarationComputer()
    {

    }

    public RedeclarationComputer(Double tora, double toda, double asda, double lda, double  dispTresh)
    {
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.dispTresh = dispTresh;
    }

    // Methods #################################################################################################################################

    /* Checks whether we need to recalculate */
    public Boolean needsRecalculation()
    {
        return true;
    }

    /* The actual recalculation happens here */
    public void calculate()
    {

    }

    // Setters #################################################################################################################################

    public void setObstacleDetails(double obstacleX, double obstacleY, double obstacleHeight)
    {
        this.obstacleX = obstacleX;
        this.obstacleY = obstacleY;
        this.obstacleHeight = obstacleHeight;
    }

    public void setParameters(Double tora, double toda, double asda, double lda, double  dispTresh)
    {
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.dispTresh = dispTresh;
    }

    // Getters #################################################################################################################################

    public double getTora()
    {
        return tora;
    }

    public double getToda()
    {
        return toda;
    }

    public double getAsda()
    {
        return asda;
    }

    public double getLda()
    {
        return lda;
    }

    public double getDispTresh()
    {
        return dispTresh;
    }
}


