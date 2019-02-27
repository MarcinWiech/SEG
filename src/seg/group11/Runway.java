package seg.group11;

public class Runway {
    private String runwayDesignator;
    private Integer tora;
    private Integer toda;
    private Integer asda;
    private Integer lda;
    private String remarks;

    public Runway (String runwayDesignator,Integer tora, Integer toda, Integer asda, Integer lda, String remarks ) {
        this.runwayDesignator = runwayDesignator;
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.remarks = remarks;
    }

}
