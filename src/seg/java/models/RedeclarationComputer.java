package seg.java.models;

import seg.java.models.Runway;

import java.lang.Character;

public class RedeclarationComputer
{

//==================================================================================================================================
//  Fields
//==================================================================================================================================

    private double obstacleXL;
    private double obstacleXR;
    private double obstacleY;
    private double obstacleHeight;

    private Runway runway;
    private char runwayDirection;
    private double tora;
    private double toda;
    private double asda;
    private double lda;
    private double dispTresh;

    private int calculationCase;

    private String toraBD = "";
    private String todaBD = "";
    private String asdaBD = "";
    private String ldaBD = "";

//==================================================================================================================================
//  Constants
//==================================================================================================================================

    private final double BLAST_PROTECTION = 300;                // 300-500 depending on the aircraft
    private final double RESA = 240;
    private final double STRIP_END = 60;
    private final double SLOPE_INVERSE = 50;

//==================================================================================================================================
//  Constructors
//==================================================================================================================================

    public RedeclarationComputer()
    {

    }

//==================================================================================================================================
//  Methods
//==================================================================================================================================

    public Boolean needsRecalculation(double obstacleXL, double obstacleXR, double obstacleY)
    {

        if(obstacleXL < -60 || obstacleXL > tora + 60)
        {
            return false;
        }

        if(obstacleY < -75 || obstacleY > 75)
        {
            return false;
        }

        return true;
    }

    // The actual recalculation happens here
    public void calculate()
    {
        double localTora = 0;
        double localToda = 0;
        double localAsda = 0;
        double localLda = 0;

        calculationCase = determineCalculationCase();
        if (calculationCase == 1)
        {
            calculateCase1(localTora, localToda, localAsda, localLda);
        }
        else if (calculationCase == 2)
        {
            calculateCase2(localTora, localToda, localAsda, localLda);
        }
        else if (calculationCase == 3)
        {
            calculateCase3(localTora, localToda, localAsda, localLda);
        }
        else
        {
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
    private int determineCalculationCase()
    {
//        System.out.println("Runway direction = " + runwayDirection);
        if(obstacleXL < tora / 2)       // for this one you might want to consider the R dispTresh
        {
            if(runwayDirection == 'R')
                return 2;
            else
                return 3;
        }
        else
        {
            if(runwayDirection == 'R')
                return 1;
            else
                return 4;
        }
    }

    // TAKE OFF AWAY/LANDING OVER + R
    private void calculateCase1(double localTora, double localToda, double localAsda, double localLda)
    {
        //  This one definitely must be checked later
        localTora = tora - dispTresh - obstacleXR - BLAST_PROTECTION ;

        localToda = localTora + calculateClearway();

        localAsda = localTora + calculateStopway();;

        localLda = lda - obstacleXR - Math.max(Math.max(SLOPE_INVERSE * obstacleHeight, RESA) + STRIP_END, BLAST_PROTECTION);

        setCase1BD(localTora, localToda, localAsda, localLda);

        //  We update the values after calculation
        setParameters(localTora, localToda, localAsda, localLda);
    }

    // TAKE OFF TOWARDS/LANDING TOWARDS + R
    private void calculateCase2(double localTora, double localToda, double localAsda, double localLda)
    {
        localTora = obstacleXR + dispTresh - Math.max(SLOPE_INVERSE * obstacleHeight, RESA) - STRIP_END;

        localToda = localTora;

        localAsda = localTora;

        localLda = obstacleXR - RESA - STRIP_END;

        setCase2BD(localTora, localToda, localAsda, localLda);

        //  We update the values after calculation
        setParameters(localTora, localToda, localAsda, localLda);
    }

    // TAKE OFF AWAY/LANDING OVER + L
    private void calculateCase3(double localTora, double localToda, double localAsda, double localLda)
    {
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
    private void calculateCase4(double localTora, double localToda, double localAsda, double localLda)
    {
        localTora = dispTresh + obstacleXL - Math.max(SLOPE_INVERSE * obstacleHeight, RESA) - STRIP_END;

        localToda = localTora;

        localAsda = localTora;

        localLda = obstacleXL - RESA - STRIP_END;

        setCase4BD(localTora, localToda, localAsda, localLda);

        //  We update the values after calculation
        setParameters(localTora, localToda, localAsda, localLda);
    }

    public double calculateStopway()
    {
        return asda - tora;
    }

    public double calculateClearway()
    {
        return toda - tora;
    }

//==================================================================================================================================
//  Setters
//==================================================================================================================================

    //  Sets the runway for which we calculate
    public void setRunway(Runway runway)
    {
        this.runway = runway;
        this.runwayDirection = Character.toUpperCase(runway.getRunwayName().charAt(2));
        this.tora = runway.getTora();
        this.toda = runway.getToda();
        this.asda = runway.getAsda();
        this.lda = runway.getLda();
        this.dispTresh = runway.getDisplacedThreshold();
    }

    public void setObstacleDetails(double obstacleXL, double obstacleXR, double obstacleY, double obstacleHeight)
    {
        this.obstacleXL = obstacleXL;
        this.obstacleXR = obstacleXR;
        this.obstacleY = obstacleY;
        this.obstacleHeight = obstacleHeight;
    }

    public void setParameters(double tora, double toda, double asda, double lda)
    {
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
    }

    private void setCase1BD(double localTora, double localToda, double localAsda, double localLda)
    {
        toraBD = " = Original TORA - Displaced Treshold - Distance from Treshold - Blast Protection\n = " +
                Double.toString(tora) + " - " + Double.toString(dispTresh) + " - " +
                Double.toString(obstacleXR) + " - " + Double.toString(BLAST_PROTECTION) + "\n" +
                " = " + Double.toString(localTora);

        todaBD = " = (R) TORA + Clearway\n = " + Double.toString(localTora) + " + " + Double.toString(calculateClearway()) +
                "\n = " + Double.toString(localToda);

        asdaBD = " = (R) TORA + Stopway\n = " + Double.toString(localTora) + " + " + Double.toString(calculateStopway()) +
                "\n = " + Double.toString(localAsda);

        ldaBD = " = Original LDA - Distance from Treshold - max(max(Slope Calculation, RESA) + Strip End, Blast Protection)\n = " +
                Double.toString(lda) + " - " + Double.toString(obstacleXR) + " - " +
                Double.toString(Math.max(Math.max(SLOPE_INVERSE * obstacleHeight, RESA) + STRIP_END, BLAST_PROTECTION))
                + "\n = " + Double.toString(localLda);
    }

    private void setCase2BD(double localTora, double localToda, double localAsda, double localLda)
    {
        toraBD = " = Displaced Treshold + Distance from Treshhold - max(Slope Calculation, RESA) - Strip End\n = " +
                Double.toString(dispTresh) + " + " + Double.toString(obstacleXR) + " - " +
                Double.toString(Math.max(SLOPE_INVERSE * obstacleHeight, RESA)) + " - " + Double.toString(STRIP_END) + "\n" +
                " = " + Double.toString(localTora);

        todaBD = " = (R) TORA\n = " + Double.toString(localTora);

        asdaBD = " = (R) TORA\n = " + Double.toString(localTora);

        ldaBD = " = Distance from Treshold - RESA - Strip End\n = " +
                Double.toString(obstacleXR) + " - " + Double.toString(RESA) + " - " +
                Double.toString(STRIP_END) + "\n = " + Double.toString(localLda);
    }

    private void setCase3BD(double localTora, double localToda, double localAsda, double localLda)
    {
        toraBD = " = Original TORA - Displaced Treshold - Distance from Treshold - Blast Protection\n = " +
                Double.toString(tora) + " - " + Double.toString(dispTresh) + " - " +
                Double.toString(obstacleXL) + " - " + Double.toString(BLAST_PROTECTION) + "\n" +
                " = " + Double.toString(localTora);

        todaBD = " = (R) TORA + Clearway\n = " + Double.toString(localTora) + " + " + Double.toString(calculateClearway()) +
                "\n = " + Double.toString(localToda);

        asdaBD = " = (R) TORA + Stopway\n = " + Double.toString(localTora) + " + " + Double.toString(calculateStopway()) +
                "\n = " + Double.toString(localAsda);

        ldaBD = " = Original LDA - Distance from Treshold - max (max(Slope Calculation, RESA) + Strip End, Blast Protection)\n = " +
                Double.toString(lda) + " - " + Double.toString(obstacleXL) + " - " +
                Double.toString(Math.max(Math.max(SLOPE_INVERSE * obstacleHeight, RESA) + STRIP_END, BLAST_PROTECTION)) + "\n = " +
                Double.toString(localLda);
    }

    private void setCase4BD(double localTora, double localToda, double localAsda, double localLda)
    {
        toraBD = " = Displaced Treshold + Distance from Treshold - Max(Slope calculation, RESA) - Strip End\n = " +
                Double.toString(dispTresh) + " + " + Double.toString(obstacleXL) + " - " +
                Double.toString(Math.max(SLOPE_INVERSE * obstacleHeight, RESA)) + " - " + STRIP_END + "\n"
                + " = " + Double.toString(localTora);

        todaBD = " = (R) TORA\n = " + Double.toString(localTora);

        asdaBD = " = (R) TORA\n = " + Double.toString(localTora);

        ldaBD = " = Distance from Treshold - RESA - Strip End\n = " +
                Double.toString(obstacleXL) + " - " + Double.toString(RESA) + " - " + Double.toString(STRIP_END) + "\n = " +
                Double.toString(localLda);
    }

//==================================================================================================================================
//  Getters
//==================================================================================================================================

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

    public double getObstacleXL()
    {
        return obstacleXL;
    }

    public double getObstacleXR()
    {
        return obstacleXR;
    }

    public double getObstacleY()
    {
        return obstacleY;
    }

    public double getObstacleHeight()
    {
        return obstacleHeight;
    }

    public String getToraBD()
    {
        return toraBD;
    }

    public String getTodaBD()
    {
        return todaBD;
    }

    public String getAsdaBD()
    {
        return asdaBD;
    }

    public String getLdaBD()
    {
        return ldaBD;
    }

//==================================================================================================================================
//  Exceptions
//==================================================================================================================================

    public class InvalidActionException extends Throwable
    {
        public InvalidActionException(String s)
        {
            System.out.println(s);
        }
    }
}


