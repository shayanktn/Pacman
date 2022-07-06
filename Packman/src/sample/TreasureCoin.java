package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TreasureCoin extends Coin {
    @Override
    Circle build(int xc, int yc) {
        Circle circle = new Circle(xc, yc, 10, Color.GOLD);
        return circle;
    }
}
