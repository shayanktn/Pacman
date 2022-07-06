package sample;

import javafx.scene.shape.Ellipse;

public class Ghosts {
    private int x, y;
    private boolean isPlaced = false;

    Ellipse build(int xg, int yg) {
        return new Ellipse();
    }

    public int getX() {
        return x;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }
}
