package seg.java.models;

import javax.naming.NamingException;

public class Runway
{
    private Runway reciprocalRunway;
    private String reciprocalName;
    private String runwayName;
    private Double tora;
    private Double toda;
    private Double asda;
    private Double lda;
    private Double displacedThreshold;

    public Runway (String runwayName, String reciprocalName, Double tora, Double toda, Double asda, Double lda, Double displacedThreshold )
    {
        this.runwayName = runwayName;
        this.reciprocalName = reciprocalName;
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.displacedThreshold = displacedThreshold;
    }

    public void setReciprocal(Runway reciprocalRunway)
    {
        this.reciprocalRunway = reciprocalRunway;
    }

    public void setParameters(Double tora, Double toda, Double asda, Double lda)
    {
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
    }

    public Runway getReciprocalRunway()
    {
        return reciprocalRunway;
    }

    public String getReciprocalName()
    {
        return reciprocalName;
    }

    public String getRunwayName()
    {
        return runwayName;
    }

    public Double getTora()
    {
        return tora;
    }

    public Double getToda()
    {
        return toda;
    }

    public Double getAsda()
    {
        return asda;
    }

    public Double getLda()
    {
        return lda;
    }

    public Double getDisplacedThreshold()
    {
        return displacedThreshold;
    }
}

