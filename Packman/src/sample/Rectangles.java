package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Rectangles {
    Rectangle []rectangles = new Rectangle[9];
    void build (Group root) {
        int x=1,y=1;
        for (int i = 0; i < 9; i++) {
            rectangles[i] = new Rectangle(180, 180);
            if(i==4) {
                rectangles[i].setFill(Color.TRANSPARENT);
                rectangles[i].setStroke(Color.DARKBLUE);
                rectangles[i].setStrokeWidth(5);
            } else {
                rectangles[i].setFill(Color.DARKBLUE);
            }
            rectangles[i].setArcHeight(15);
            rectangles[i].setArcWidth(15);
            rectangles[i].setStrokeWidth(3);
            rectangles[i].setX(90*x+((x-1)*180));
            rectangles[i].setY(90*y+((y-1)*180));
            root.getChildren().add(rectangles[i]);
            x++;
            if(x==4) {
                x = 1;
                y++;
            }
        }
    }
}
