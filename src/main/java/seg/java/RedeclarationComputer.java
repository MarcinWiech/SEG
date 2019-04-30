package seg.java;

import seg.java.models.Runway;

public class RedeclarationComputer {

/*==================================================================================================================================
//  Fields
//================================================================================================================================*/

    private final Double BLAST_PROTECTION = 300.0;                // 300-500 depending on the aircraft
    private final Double RESA = 240.0;
    private final Double STRIP_END = 60.0;
    private final Double SLOPE_INVERSE = 50.0;
    private RedeclarationComputer reciprocalComputer;
    private Double obstacleXL;
    private Double obstacleXR;
    private Double obstacleY;
    private Double obstacleHeight;
    private Runway runway;
    private char runwayDirection;
    private Double tora;
    private Double toda;
    private Double asda;
    private Double lda;
    private Double dispTresh;
    private int calculationCase;

    /*==================================================================================================================================
    //  Constants
    //================================================================================================================================*/
    private String toraBD = "";
    private String todaBD = "";
    private String asdaBD = "";
    private String ldaBD = "";

/*==================================================================================================================================
//  Constructors
//================================================================================================================================*/

    public RedeclarationComputer() {

    }

/*==================================================================================================================================
//  Methods
//================================================================================================================================*/

    public Boolean needsRecalculation(Double obstacleXL, Double obstacleXR, Double obstacleY) {
        // We mirror the process for the reciprocal computer
        if (runway.getReciprocalRunway() != null && reciprocalComputer.needsRecalculationAsRecip(obstacleXL, obstacleXR, obstacleY))
            reciprocalComputer.calculate();


        // And then we carry on with the current computer
        if (obstacleXL < -60 || obstacleXL > tora + 60) {
            return false;
        }

        return obstacleY >= -75 && obstacleY <= 75;
    }

    public Boolean needsRecalculationAsRecip(Double obstacleXL, Double obstacleXR, Double obstacleY) {
        if (obstacleXL < -60 || obstacleXL > tora + 60) {
            return false;
        }

        return obstacleY >= -75 && obstacleY <= 75;
    }

    // The actual recalculation happens here
    public void calculate() {
        Double localTora = 0.0;
        Double localToda = 0.0;
        Double localAsda = 0.0;
        Double localLda = 0.0;

        calculationCase = determineCalculationCase();
        if (calculationCase == 1) {
            calculateCase1(localTora, localToda, localAsda, localLda);
        } else if (calculationCase == 2) {
            calculateCase2(localTora, localToda, localAsda, localLda);
        } else if (calculationCase == 3) {
            calculateCase3(localTora, localToda, localAsda, localLda);
        } else {
            calculateCase4(localTora, localToda, localAsda, localLda);
        }
    }

    /**
     * Determines the value of calculationCase
     * 1 represents the action of TAKE OFF AWAY/LANDING OVER + R
     * 2 represents the action of TAKE OFF TOWARDS/LANDING TOWARDS + R
     * 3 represents the action of TAKE OFF AWAY/LANDING OVER + L
     * 4 represents the action of TAKE OFF TOWARDS/LANDING TOWARDS + L
     */
    private int determineCalculationCase() {


        if (runwayDirection == 'R' || runwayDirection == 'C')       // for this one you might want to consider the R dispTresh
        {
            if (obstacleXR < tora / 2)
                calculationCase = 1;
            else
                calculationCase = 2;
        } else {
            if (obstacleXL < tora / 2)
                calculationCase = 3;
            else
                calculationCase = 4;
        }

        return calculationCase;
    }

    // TAKE OFF AWAY/LANDING OVER + R
    private void calculateCase1(Double localTora, Double localToda, Double localAsda, Double localLda) {
        //  This one definitely must be checked later
        localTora = tora - dispTresh - obstacleXR - BLAST_PROTECTION;

        localToda = localTora + calculateClearway();

        localAsda = localTora + calculateStopway();

        localLda = lda - obstacleXR - Math.max(Math.max(SLOPE_INVERSE * obstacleHeight, RESA) + STRIP_END, BLAST_PROTECTION);

        setCase1BD(localTora, localToda, localAsda, localLda);

        //  We update the values after calculation
        setParameters(localTora, localToda, localAsda, localLda);
    }

    // TAKE OFF TOWARDS/LANDING TOWARDS + R
    private void calculateCase2(Double localTora, Double localToda, Double localAsda, Double localLda) {
        localTora = obstacleXR + dispTresh - Math.max(SLOPE_INVERSE * obstacleHeight, RESA) - STRIP_END;

        localToda = localTora;

        localAsda = localTora;

        localLda = obstacleXR - RESA - STRIP_END;

        setCase2BD(localTora, localToda, localAsda, localLda);

        //  We update the values after calculation
        setParameters(localTora, localToda, localAsda, localLda);
    }

    // TAKE OFF AWAY/LANDING OVER + L
    private void calculateCase3(Double localTora, Double localToda, Double localAsda, Double localLda) {
        //  This one definitely must be checked later
        localTora = tora - dispTresh - obstacleXL - BLAST_PROTECTION;

        localToda = localTora + calculateClearway();

        localAsda = localTora + calculateStopway();

        localLda = lda - obstacleXL - Math.max(Math.max(SLOPE_INVERSE * obstacleHeight, RESA) + STRIP_END, BLAST_PROTECTION);

        setCase3BD(localTora, localToda, localAsda, localLda);

        //  We update the values after calculation
        setParameters(localTora, localToda, localAsda, localLda);
    }

    // TAKE OFF TOWARDS/LANDING TOWARDS + L
    private void calculateCase4(Double localTora, Double localToda, Double localAsda, Double localLda) {
        localTora = dispTresh + obstacleXL - Math.max(SLOPE_INVERSE * obstacleHeight, RESA) - STRIP_END;

        localToda = localTora;

        localAsda = localTora;

        localLda = obstacleXL - RESA - STRIP_END;

        setCase4BD(localTora, localToda, localAsda, localLda);

        //  We update the values after calculation
        setParameters(localTora, localToda, localAsda, localLda);
    }

    public double calculateStopway() {
        return asda - tora;
    }

    public double calculateClearway() {
        return toda - tora;
    }

/*==================================================================================================================================
//  Setters
//================================================================================================================================*/

    //  Sets the runway for which we calculate
    public void setRunway(Runway runway) {
        this.runway = runway;
        this.runwayDirection = Character.toUpperCase(runway.getName().charAt(2));
        this.tora = runway.getTora();
        this.toda = runway.getToda();
        this.asda = runway.getAsda();
        this.lda = runway.getLda();
        this.dispTresh = runway.getThreshold();

        if (runway.getReciprocalRunway() != null)
            reciprocalComputer.setRunwayAsRecip(runway.getReciprocalRunway());
    }

    //  Sets the runway for the reciprocal computer
    public void setRunwayAsRecip(Runway runway) {
        // TODO: Refactor out duplicate code
        this.runway = runway;
        this.runwayDirection = Character.toUpperCase(runway.getName().charAt(2));
        this.tora = runway.getTora();
        this.toda = runway.getToda();
        this.asda = runway.getAsda();
        this.lda = runway.getLda();
        this.dispTresh = runway.getThreshold();
    }

    public void setObstacleDetails(Double obstacleXL, Double obstacleXR, Double obstacleY, Double obstacleHeight) {
        this.obstacleXL = obstacleXL;
        this.obstacleXR = obstacleXR;
        this.obstacleY = obstacleY;
        this.obstacleHeight = obstacleHeight;

        if (runway.getReciprocalRunway() != null)
            reciprocalComputer.setObstacleDetailsAsRecip(obstacleXL, obstacleXR, obstacleY, obstacleHeight);
    }

    public void setObstacleDetailsAsRecip(Double obstacleXL, Double obstacleXR, Double obstacleY, Double obstacleHeight) {
        this.obstacleXL = obstacleXL;
        this.obstacleXR = obstacleXR;
        this.obstacleY = obstacleY;
        this.obstacleHeight = obstacleHeight;
    }

    public void setParameters(Double tora, Double toda, Double asda, Double lda) {
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
    }

    private void setCase1BD(Double localTora, Double localToda, Double localAsda, Double localLda) {
        toraBD = " = Original TORA - Displaced Treshold - Distance from Treshold - Blast Protection\n = " +
                tora + " - " + dispTresh + " - " +
                obstacleXR + " - " + BLAST_PROTECTION + "\n" +
                " = " + localTora;

        todaBD = " = (R) TORA + Clearway\n = " + localTora + " + " + calculateClearway() +
                "\n = " + localToda;

        asdaBD = " = (R) TORA + Stopway\n = " + localTora + " + " + calculateStopway() +
                "\n = " + localAsda;

        ldaBD = " = Original LDA - Distance from Treshold - max(max(Slope Calculation, RESA) + Strip End, Blast Protection)\n = " +
                lda + " - " + obstacleXR + " - " +
                Math.max(Math.max(SLOPE_INVERSE * obstacleHeight, RESA) + STRIP_END, BLAST_PROTECTION)
                + "\n = " + localLda;
    }

    private void setCase2BD(Double localTora, Double localToda, Double localAsda, Double localLda) {
        toraBD = " = Displaced Treshold + Distance from Treshhold - max(Slope Calculation, RESA) - Strip End\n = " +
                dispTresh + " + " + obstacleXR + " - " +
                Math.max(SLOPE_INVERSE * obstacleHeight, RESA) + " - " + STRIP_END + "\n" +
                " = " + localTora;

        todaBD = " = (R) TORA\n = " + localTora;

        asdaBD = " = (R) TORA\n = " + localTora;

        ldaBD = " = Distance from Treshold - RESA - Strip End\n = " +
                obstacleXR + " - " + RESA + " - " +
                STRIP_END + "\n = " + localLda;
    }

    private void setCase3BD(Double localTora, Double localToda, Double localAsda, Double localLda) {
        toraBD = " = Original TORA - Displaced Treshold - Distance from Treshold - Blast Protection\n = " +
                tora + " - " + dispTresh + " - " +
                obstacleXL + " - " + BLAST_PROTECTION + "\n" +
                " = " + localTora;

        todaBD = " = (R) TORA + Clearway\n = " + localTora + " + " + calculateClearway() +
                "\n = " + localToda;

        asdaBD = " = (R) TORA + Stopway\n = " + localTora + " + " + calculateStopway() +
                "\n = " + localAsda;

        ldaBD = " = Original LDA - Distance from Treshold - max (max(Slope Calculation, RESA) + Strip End, Blast Protection)\n = " +
                lda + " - " + obstacleXL + " - " +
                Math.max(Math.max(SLOPE_INVERSE * obstacleHeight, RESA) + STRIP_END, BLAST_PROTECTION) + "\n = " +
                localLda;
    }

    private void setCase4BD(Double localTora, Double localToda, Double localAsda, Double localLda) {
        toraBD = " = Displaced Treshold + Distance from Treshold - Max(Slope calculation, RESA) - Strip End\n = " +
                dispTresh + " + " + obstacleXL + " - " +
                Math.max(SLOPE_INVERSE * obstacleHeight, RESA) + " - " + STRIP_END + "\n"
                + " = " + localTora;

        todaBD = " = (R) TORA\n = " + localTora;

        asdaBD = " = (R) TORA\n = " + localTora;

        ldaBD = " = Distance from Treshold - RESA - Strip End\n = " +
                obstacleXL + " - " + RESA + " - " + STRIP_END + "\n = " +
                localLda;
    }

    public RedeclarationComputer getReciprocalComputer() {
        return reciprocalComputer;
    }

/*==================================================================================================================================
//  Getters
//================================================================================================================================*/

    public void setReciprocalComputer(RedeclarationComputer reciprocalComputer) {
        this.reciprocalComputer = reciprocalComputer;
    }

    public Double getTora() {
        return tora;
    }

    public Double getToda() {
        return toda;
    }

    public Double getAsda() {
        return asda;
    }

    public Double getLda() {
        return lda;
    }

    public Double getDispTresh() {
        return dispTresh;
    }

    public Double getObstacleXL() {
        return obstacleXL;
    }

    public Double getObstacleXR() {
        return obstacleXR;
    }

    public Double getObstacleY() {
        return obstacleY;
    }

    public Double getObstacleHeight() {
        return obstacleHeight;
    }

    public String getToraBD() {
        return toraBD;
    }

    public String getTodaBD() {
        return todaBD;
    }

    public String getAsdaBD() {
        return asdaBD;
    }

    public String getLdaBD() {
        return ldaBD;
    }

    public int getCalculationCase() {
        return calculationCase;
    }

    public Double getAppropriateX() {
        if (runway.getName().charAt(2) == 'L')
            return obstacleXL;
        else
            return obstacleXR;
    }

    public Runway getRunway()
    {
        return runway;
    }


/*==================================================================================================================================
//  Exceptions
//================================================================================================================================*/

    public class InvalidActionException extends Throwable {
        public InvalidActionException(String s) {
            System.out.println(s);
        }
    }
}


