package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class Main extends Application {

    int time = 0;

    boolean ghostStop = false;
    int stopTimer = 0;

    Coin[] coin = new Coin[136];

    Coin[] normalCoins = new NormalCoin[124];
    Coin[] treasureCoins = new TreasureCoin[4];
    Coin[] stopperCoins = new StopperCoin[4];
    Coin[] penaltyCoins = new PenaltyCoin[4];

    Ghosts[] ghosts = new Ghosts[5];

    void coin(Group root) {

        for (int i = 0; i < 136; i++) {
            coin[i] = new Coin();
        }
        for (int i = 0; i < 124; i++) {
            normalCoins[i] = new NormalCoin();
        }
        for (int i = 0; i < 4; i++) {
            treasureCoins[i] = new TreasureCoin();
            stopperCoins[i] = new StopperCoin();
            penaltyCoins[i] = new PenaltyCoin();
        }

        int xc = 45, yc = 45, nc = 0, tc = 0, sc = 0, pc = 0, c = 0;

        for (int l = 0; l < 4; l++) {
            for (int i = 0; i < 19; i++) {
                if (xc == 45 && yc == 45 || xc == 45 && yc == 855 || xc == 855 && yc == 45 || xc == 855 && yc == 855) {
                    Circle circle = stopperCoins[sc].build(xc, yc);
                    root.getChildren().add(circle);
                    stopperCoins[sc].setX(xc);
                    stopperCoins[sc].setY(yc);
                    coin[c] = stopperCoins[sc];
                    c++;
                    xc += 45;
                    sc++;
                    continue;
                } else if (xc == 720 && yc == 315 || xc == 180 && yc == 315 || xc == 180 && yc == 585 || xc == 720 && yc == 585) {
                    Circle circle = penaltyCoins[pc].build(xc, yc);
                    root.getChildren().add(circle);
                    penaltyCoins[pc].setX(xc);
                    penaltyCoins[pc].setY(yc);
                    coin[c] = penaltyCoins[pc];
                    c++;
                    xc += 45;
                    pc++;
                    continue;
                } else if (xc == 450 && yc == 315 || xc == 450 && yc == 585) {
                    Circle circle = treasureCoins[tc].build(xc, yc);
                    root.getChildren().add(circle);
                    treasureCoins[tc].setX(xc);
                    treasureCoins[tc].setY(yc);
                    coin[c] = treasureCoins[tc];
                    c++;
                    xc += 45;
                    tc++;
                    continue;
                }
                Circle circle = normalCoins[nc].build(xc, yc);
                normalCoins[nc].setX(xc);
                normalCoins[nc].setY(yc);
                coin[c] = normalCoins[nc];
                c++;
                root.getChildren().add(circle);
                xc += 45;
                nc++;
            }
            xc = 45;
            yc += 6 * 45;
        }
        xc = 45;
        yc = 45;
        for (int u = 0; u < 4; u++) {
            for (int i = 0; i < 19; i++) {
                if (xc == 315 && yc == 450 || xc == 585 && yc == 450) {
                    Circle circle = treasureCoins[tc].build(xc, yc);
                    root.getChildren().add(circle);
                    treasureCoins[tc].setX(xc);
                    treasureCoins[tc].setY(yc);
                    coin[c] = treasureCoins[tc];
                    c++;
                    yc += 45;
                    tc++;
                    continue;
                } else if (xc == 45 && yc == 45 || xc == 45 && yc == 315 || xc == 45 && yc == 585 || xc == 45 && yc == 855 ||
                        xc == 315 && yc == 45 || xc == 315 && yc == 315 || xc == 315 && yc == 585 || xc == 315 && yc == 855 ||
                        xc == 585 && yc == 45 || xc == 585 && yc == 315 || xc == 585 && yc == 585 || xc == 585 && yc == 855 ||
                        xc == 855 && yc == 45 || xc == 855 && yc == 315 || xc == 855 && yc == 585 || xc == 855 && yc == 855) {
                    yc += 45;
                    continue;
                }
                Circle circle = normalCoins[nc].build(xc, yc);
                root.getChildren().add(circle);
                normalCoins[nc].setX(xc);
                normalCoins[nc].setY(yc);
                coin[c] = normalCoins[nc];
                c++;
                yc += 45;
                nc++;
            }
            yc = 45;
            xc += 6 * 45;
        }
    }

    void newCoin(Group root) {
        for (int i = 0; i < 136; i++) {
            if (coin[i].isEaten()) {
                continue;
            }
            root.getChildren().add(coin[i].build(coin[i].getX(), coin[i].getY()));
        }
    }

    void checkEaten(int x, int y, Group root) {
        for (int i = 0; i < 136; i++) {
            if (coin[i].getX() == x && coin[i].getY() == y) {
                coin[i].setEaten(true);
                newCoin(root);
                break;
            }
        }
    }

    void scoreCoin(PacMan player) {
        int tc = 0;
        ArrayList<Integer> arrayList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 136; i++) {
            if (coin[i].isEaten() && !coin[i].isChecked() && coin[i].getClass().toString().equals("class sample.NormalCoin")) {
                coin[i].setChecked(true);
                player.score();
            }
            if (coin[i].isEaten() && coin[i].getClass().toString().equals("class sample.PenaltyCoin") && !coin[i].isChecked()) {
                for (int j = 0; j < 136; j++) {
                    if (coin[j].isEaten() && coin[j].getClass().toString().equals("class sample.NormalCoin"))
                        tc++;
                }
                if (tc <= 5) {
                    for (int j = 0; j < 136; j++) {
                        if (coin[j].isEaten() && coin[j].getClass().toString().equals("class sample.NormalCoin"))
                            coin[j].setEaten(false);
                    }
                } else {
                    for (int j = 0; j < 136; j++) {
                        if (coin[j].isEaten() && coin[j].getClass().toString().equals("class sample.NormalCoin"))
                            arrayList.add(j);
                    }
                    for (int j = 0; j < 5; j++) {
                        coin[arrayList.get(random.nextInt(arrayList.size()))].setEaten(false);
                    }
                }
                coin[i].setChecked(true);
            }
            if (coin[i].isEaten() && coin[i].getClass().toString().equals("class sample.TreasureCoin") && !coin[i].isChecked()) {
                for (int j = 0; j < 136; j++) {
                    if (!coin[j].isEaten() && coin[j].getClass().toString().equals("class sample.NormalCoin"))
                        tc++;
                }
                if (tc <= 5) {
                    for (int j = 0; j < 136; j++) {
                        if (!coin[j].isEaten() && coin[j].getClass().toString().equals("class sample.NormalCoin"))
                            coin[j].setEaten(true);
                    }
                } else {
                    for (int j = 0; j < 136; j++) {
                        if (!coin[j].isEaten() && coin[j].getClass().toString().equals("class sample.NormalCoin"))
                            arrayList.add(j);
                    }
                    for (int j = 0; j < 5; j++) {
                        coin[arrayList.get(random.nextInt(arrayList.size()))].setEaten(true);
                    }
                }
                coin[i].setChecked(true);
            }
            if (coin[i].isEaten() && coin[i].getClass().toString().equals("class sample.StopperCoin") && !coin[i].isChecked()) {
                ghostStop = true;
                stopTimer = 5;
                coin[i].setChecked(true);
            }
        }
    }

    void setGhosts(Group root) {
        ghosts[0] = new HGhost();
        ghosts[1] = new VGhost();
        ghosts[2] = new HVGhost();
        ghosts[3] = new FastGhost();
        ghosts[4] = new SmartGhost();

        ghosts[0].setXY(405, 405);
        ghosts[1].setXY(405, 495);
        ghosts[2].setXY(450, 450);
        ghosts[3].setXY(495, 405);
        ghosts[4].setXY(495, 495);

        for (int i = 0; i < 5; i++) {
            root.getChildren().add(ghosts[i].build(ghosts[i].getX(), ghosts[i].getY()));
        }
    }

    void moveGhost(int i, Group root, Circle packMan, Rectangles rectangles, Stage primaryStage) {
        if (!ghostStop && stopTimer == 0) {
            switch (i) {
                case 0 -> {
                    if (!ghosts[0].isPlaced()) {
                        ghosts[0].setXY(450, 585);
                        ghosts[0].setPlaced(true);
                        break;
                    }
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        if (ghosts[0].getX() < 855)
                            ghosts[0].setXY(ghosts[0].getX() + 45, ghosts[0].getY());
                        else
                            ghosts[0].setXY(ghosts[0].getX() - 45, ghosts[0].getY());
                    } else {
                        if (ghosts[0].getX() > 45)
                            ghosts[0].setXY(ghosts[0].getX() - 45, ghosts[0].getY());
                        else
                            ghosts[0].setXY(ghosts[0].getX() + 45, ghosts[0].getY());
                    }
                }

                case 1 -> {
                    if (!ghosts[1].isPlaced()) {
                        ghosts[1].setXY(585, 450);
                        ghosts[1].setPlaced(true);
                        break;
                    }
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        if (ghosts[1].getY() > 45)
                            ghosts[1].setXY(ghosts[1].getX(), ghosts[1].getY() - 45);
                        else
                            ghosts[1].setXY(ghosts[1].getX(), ghosts[1].getY() + 45);
                    } else {
                        if (ghosts[1].getY() < 855)
                            ghosts[1].setXY(ghosts[1].getX(), ghosts[1].getY() + 45);
                        else
                            ghosts[1].setXY(ghosts[1].getX(), ghosts[1].getY() - 45);
                    }
                }

                case 2 -> {
                    if (!ghosts[2].isPlaced()) {
                        ghosts[2].setXY(315, 450);
                        ghosts[2].setPlaced(true);
                        break;
                    }

                    Random random = new Random();
                    int oldX = ghosts[2].getX(), oldY = ghosts[2].getY();
                    boolean yGrant = grantY(), xGrant = grantX();

                    if (xGrant && !yGrant) {
                        if (random.nextBoolean())
                            ghosts[2].setXY(ghosts[2].getX() + 45, ghosts[2].getY());
                        else
                            ghosts[2].setXY(ghosts[2].getX() - 45, ghosts[2].getY());
                    }
                    if (yGrant && !xGrant) {
                        if (random.nextBoolean())
                            ghosts[2].setXY(ghosts[2].getX(), ghosts[2].getY() + 45);
                        else
                            ghosts[2].setXY(ghosts[2].getX(), ghosts[2].getY() - 45);
                    }
                    if (yGrant && xGrant) {
                        if (random.nextBoolean()) {
                            if (random.nextBoolean())
                                ghosts[2].setXY(ghosts[2].getX(), ghosts[2].getY() + 45);
                            else
                                ghosts[2].setXY(ghosts[2].getX(), ghosts[2].getY() - 45);
                        } else {
                            if (random.nextBoolean())
                                ghosts[2].setXY(ghosts[2].getX() + 45, ghosts[2].getY());
                            else
                                ghosts[2].setXY(ghosts[2].getX() - 45, ghosts[2].getY());
                        }
                    }

                    if (ghosts[2].getX() > 855 || ghosts[2].getX() < 45)
                        ghosts[2].setXY(oldX, ghosts[2].getY());

                    if (ghosts[2].getY() > 855 || ghosts[2].getY() < 45)
                        ghosts[2].setXY(ghosts[2].getY(), oldY);

                }

                case 3 -> {
                    float upX, downX, leftX, rightX, upY, downY, leftY, rightY;
                    float[] line = new float[4];

                    if (!ghosts[3].isPlaced()) {
                        ghosts[3].setXY(450, 315);
                        ghosts[3].setPlaced(true);
                        break;
                    }

                    upX = ghosts[3].getX();
                    upY = ghosts[3].getY() - 45.0f;
                    downX = ghosts[3].getX();
                    downY = ghosts[3].getY() + 45.0f;
                    leftX = ghosts[3].getX() - 45.0f;
                    leftY = ghosts[3].getY();
                    rightX = ghosts[3].getX() + 45.0f;
                    rightY = ghosts[3].getY();

                    line[0] = (float) distance(upX, upY, (float) packMan.getCenterX(), (float) packMan.getCenterY());
                    line[1] = (float) distance(downX, downY, (float) packMan.getCenterX(), (float) packMan.getCenterY());
                    line[2] = (float) distance(leftX, leftY, (float) packMan.getCenterX(), (float) packMan.getCenterY());
                    line[3] = (float) distance(rightX, rightY, (float) packMan.getCenterX(), (float) packMan.getCenterY());

                    int index = 0;
                    int min = (int) line[index];

                    for (int j = 0; j < line.length; j++) {
                        if (line[j] <= min) {
                            min = (int) line[j];
                            index = j;
                        }
                    }

                    switch (index) {
                        case 0 -> ghosts[3].setXY((int) upX, (int) upY);
                        case 1 -> ghosts[3].setXY((int) downX, (int) downY);
                        case 2 -> ghosts[3].setXY((int) leftX, (int) leftY);
                        case 3 -> ghosts[3].setXY((int) rightX, (int) rightY);
                    }
                }
                /*
                case 4 ->
                 */
            }
        }
        for (int j = 0; j < 5; j++) {
            if (ghosts[j].getX() == packMan.getCenterX() && ghosts[j].getY() == packMan.getCenterY()) {
                primaryStage.close();
            }
        }
        newGhosts(root, packMan, rectangles);
    }

    void newGhosts(Group root, Circle packMan, Rectangles rectangles) {
        root.getChildren().clear();
        root.getChildren().add(packMan);
        rectangles.build(root);
        checkEaten((int) packMan.getCenterX(), (int) packMan.getCenterY(), root);
        for (int k = 0; k < 5; k++) {
            root.getChildren().add(ghosts[k].build(ghosts[k].getX(), ghosts[k].getY()));
        }
    }

    void stopGhosts() {
        if (ghostStop) {
            stopTimer--;
            System.out.println("Stop");
        }

        if (stopTimer == 0)
            ghostStop = false;
    }

    double distance(float x1, float y1, float x2, float y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    boolean grantX() {
        boolean xGrant = false;

        for (int j = 0; j < 136; j++) {
            if (ghosts[2].getY() == coin[j].getY() && ghosts[2].getX() + 45 == coin[j].getX() || ghosts[2].getY() == coin[j].getY() && ghosts[2].getX() - 45 == coin[j].getX()) {
                xGrant = true;
                break;
            }
        }
        return xGrant;
    }

    boolean grantY() {
        boolean yGrant = false;
        for (int j = 0; j < 136; j++) {
            if (ghosts[2].getX() == coin[j].getX() && ghosts[2].getY() + 45 == coin[j].getY() || ghosts[2].getX() == coin[j].getX() && ghosts[2].getY() - 45 == coin[j].getY()) {
                yGrant = true;
                break;
            }
        }
        return yGrant;
    }

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        primaryStage.setTitle("PacMan");
        Scene scene = new Scene(root, 900, 900, Color.BLACK);

        Rectangles rectangles = new Rectangles();
        rectangles.build(root);

        PacMan player = new PacMan();
        Circle packMan = player.pacManCreator();

        coin(root);
        checkEaten((int) packMan.getCenterX(), (int) packMan.getCenterY(), root);

        setGhosts(root);

        root.getChildren().add(packMan);
        primaryStage.setScene(scene);
        primaryStage.show();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                scene.setOnKeyPressed(event -> {
                    int x = 45, y = 45;
                    for (int i = 0; i < 4; i++) {
                        if (packMan.getCenterX() == x) {
                            player.setMoveY(true);
                            break;
                        }
                        x += 270;
                    }
                    for (int i = 0; i < 4; i++) {
                        if (packMan.getCenterY() == y) {
                            player.setMoveX(true);
                            break;
                        }
                        y += 270;
                    }
                    for (int j = 0; j < 5; j++) {
                        if (ghosts[j].getX() == packMan.getCenterX() && ghosts[j].getY() == packMan.getCenterY()) {
                            primaryStage.close();
                        }
                    }

                    if (event.getCode() == KeyCode.W && packMan.getCenterY() != 45 && player.isMoveY()) {
                        root.getChildren().clear();
                        packMan.setCenterY(packMan.getCenterY() - 45);
                        root.getChildren().add(packMan);
                        rectangles.build(root);
                        System.out.println("___________");
                        System.out.println("Y : " + packMan.getCenterY());
                        System.out.println("X : " + packMan.getCenterX());
                        checkEaten((int) packMan.getCenterX(), (int) packMan.getCenterY(), root);
                        newGhosts(root, packMan, rectangles);
                    }
                    if (event.getCode() == KeyCode.S && packMan.getCenterY() != 855 && player.isMoveY()) {
                        root.getChildren().clear();
                        packMan.setCenterY(packMan.getCenterY() + 45);
                        root.getChildren().add(packMan);
                        rectangles.build(root);
                        System.out.println("___________");
                        System.out.println("Y : " + packMan.getCenterY());
                        System.out.println("X : " + packMan.getCenterX());
                        checkEaten((int) packMan.getCenterX(), (int) packMan.getCenterY(), root);
                        newGhosts(root, packMan, rectangles);
                    }
                    if (event.getCode() == KeyCode.A && packMan.getCenterX() != 45 && player.isMoveX()) {
                        root.getChildren().clear();
                        packMan.setCenterX(packMan.getCenterX() - 45);
                        root.getChildren().add(packMan);
                        rectangles.build(root);
                        System.out.println("___________");
                        System.out.println("Y : " + packMan.getCenterY());
                        System.out.println("X : " + packMan.getCenterX());
                        checkEaten((int) packMan.getCenterX(), (int) packMan.getCenterY(), root);
                        newGhosts(root, packMan, rectangles);
                    }
                    if (event.getCode() == KeyCode.D && packMan.getCenterX() != 855 && player.isMoveX()) {
                        root.getChildren().clear();
                        packMan.setCenterX(packMan.getCenterX() + 45);
                        root.getChildren().add(packMan);
                        rectangles.build(root);
                        System.out.println("___________");
                        System.out.println("Y : " + packMan.getCenterY());
                        System.out.println("X : " + packMan.getCenterX());
                        checkEaten((int) packMan.getCenterX(), (int) packMan.getCenterY(), root);
                        newGhosts(root, packMan, rectangles);
                    }
                    if (event.getCode() == KeyCode.Q) {
                        packMan.setFill(Color.RED);
                    }
                    if (event.getCode() == KeyCode.E) {
                        packMan.setFill(Color.BLUE);
                    }
                    if (event.getCode() == KeyCode.R) {
                        packMan.setFill(Color.GREEN);
                    }
                    if (event.getCode() == KeyCode.F) {
                        packMan.setFill(Color.RED);
                    }
                    if (event.getCode() == KeyCode.Y) {
                        packMan.setFill(Color.YELLOW);
                    }
                    if (event.getCode() == KeyCode.Z) {
                        scene.setFill(Color.GOLDENROD);
                    }
                    if (event.getCode() == KeyCode.X) {
                        scene.setFill(Color.DEEPPINK);
                    }
                    if (event.getCode() == KeyCode.B) {
                        scene.setFill(Color.BLACK);
                    }
                    player.setMoveX(false);
                    player.setMoveY(false);
                });
            }
        };
        new Timer().schedule(task, 0, 10000);


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            scoreCoin(player);
            time++;
            if (!ghostStop) {
                moveGhost(0, root, packMan, rectangles, primaryStage);
                if (time > 2)
                    moveGhost(1, root, packMan, rectangles, primaryStage);
                if (time > 4)
                    moveGhost(2, root, packMan, rectangles, primaryStage);
                if (time > 6)
                    moveGhost(3, root, packMan, rectangles, primaryStage);
            } else {
                stopGhosts();
            }

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}