package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PacMan {
    private boolean moveX = false;
    private boolean moveY = false;
    private int score = 0;

    public Circle pacManCreator() {
        Circle pacMan = new Circle(450,45,30, Color.YELLOW);
        return pacMan;
    }

    public boolean isMoveX() {
        return moveX;
    }

    public void setMoveX(boolean moveX) {
        this.moveX = moveX;
    }

    public boolean isMoveY() {
        return moveY;
    }

    public void setMoveY(boolean moveY) {
        this.moveY = moveY;
    }

    public int getScore() {
        return score;
    }

    public int score() {
        this.score++;
        return 0;
    }
}
