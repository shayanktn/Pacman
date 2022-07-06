package sample;

import javafx.scene.shape.Circle;

public class Coin {
    private int x,y;
    private boolean isEaten = false;
    private boolean isChecked = false;

    Circle build(int xc,int yc) {
        return new Circle();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
