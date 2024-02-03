package kameib.localizator.data;

public class Drawing {
    public int u, v;
    public int width, height;
    public Drawing(int xStartPos, int yStartPos, int width, int height) {
        this.u = xStartPos;
        this.v = yStartPos;
        this.width = width;
        this.height = height;
    }
}
