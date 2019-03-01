package seg.java;

public class Runway {
    private String runwayName;
    private Integer tora;
    private Integer toda;
    private Integer asda;
    private Integer lda;
    private Integer displacedThreshold;

    public Runway(String runwayName, Integer tora, Integer toda, Integer asda, Integer lda, Integer displacedThreshold) {
        this.runwayName = runwayName;
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.displacedThreshold = displacedThreshold;
    }

    /**
     * GETTERS
     **/
    public String getRunwayName() {
        return runwayName;
    }

    public Integer getTora() {
        return tora;
    }

    public Integer getToda() {
        return toda;
    }

    public Integer getAsda() {
        return asda;
    }

    public Integer getLda() {
        return lda;
    }

    public Integer getDisplacedThreshold() {
        return displacedThreshold;
    }
}
