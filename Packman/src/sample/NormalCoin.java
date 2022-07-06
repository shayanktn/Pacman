package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NormalCoin extends Coin {

    @Override
    Circle build(int xc, int yc) {
        Circle circle = new Circle(xc, yc, 10, Color.WHITE);
        return circle;
    }
}
