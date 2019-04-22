package seg.java.models;

public class Obstacle {

    private String name;
    private float xl;
    private float xr;
    private float height;
    private float y;

    public Obstacle(String name, float xl, float xr, float height, float y){
        this.name = name;
        this.xl = xl;
        this.xr = xr;
        this.y = y;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public float getXl() {
        return xl;
    }

    public float getXr() {
        return xr;
    }

    public float getHeight() {
        return height;
    }

    public float getY() {
        return y;
    }
}
