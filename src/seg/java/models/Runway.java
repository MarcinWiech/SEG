package seg.java.models;

public class Runway
{
    private Runway reciprocalRunway;
    private String reciprocalName;
    private String runwayName;
    private Integer tora;
    private Integer toda;
    private Integer asda;
    private Integer lda;
    private Integer displacedThreshold;

    public Runway (String reciprocalName, String runwayName,Integer tora, Integer toda, Integer asda, Integer lda, Integer displacedThreshold )
    {
        this.reciprocalName = reciprocalName;
        this.runwayName = runwayName;
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

    public Integer getTora()
    {
        return tora;
    }

    public Integer getToda()
    {
        return toda;
    }

    public Integer getAsda()
    {
        return asda;
    }

    public Integer getLda()
    {
        return lda;
    }

    public Integer getDisplacedThreshold()
    {
        return displacedThreshold;
    }
}
