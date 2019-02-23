/*
 * CS1021 - 051
 * Winter 2018-2019
 * Lab Game Of Life
 * Name: Brendan Ecker
 * Created 1/10/2019
 */
package eckerb;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


/**
 * This class uses the LifeGrid to make the game,
 * The Game of Life.
 */
public class Lab5 extends Application {
    private final int WIDTH = 400;
    private final int HEIGHT = 500;
    private LifeGrid game;
    private Label cellDead;
    private Label cellAlive;
    private int cellsDead;
    private int cellsAlive;

    /**
     * The start method that creates
     * the pane, game, scene and title.
     *
     * @param stage The stage where the scene is set and shown.
     */
    public void start(Stage stage) {

        FlowPane pane = new FlowPane();
        game = new LifeGrid(pane, WIDTH, HEIGHT - 100); // to make room for the buttons and the cell counts.
        Scene scene = new Scene(pane, WIDTH, HEIGHT);

        for (int i = 0; i < game.getNumberOfCellsHigh(); i++) {  // the position in the column.
            for (int x = 0; x < game.getNumberOfCellsWide(); x++) { // the position in the row.
                game.getCells().get(i).get(x).setOnMouseClicked(this::cellRespond);
            }
        }
        createContents(pane);

        stage.setTitle("The game of Life.");
        stage.setScene(scene);
        stage.show();
    }

    private void createContents(FlowPane pane) {

        Button buttonReset = new Button("Reset");
        buttonReset.setOnMouseClicked(this::resetRespond);
        Button buttonRun = new Button("Run");
        buttonRun.setOnMouseClicked(this::runRespond);
        cellCount();

        cellAlive = new Label("Alive:" + cellsAlive);
        cellDead = new Label("   Dead:" + cellsDead);

        pane.getChildren().addAll(buttonReset, buttonRun, cellAlive, cellDead);
    }

    private void runRespond(MouseEvent e) {
        if (cellsAlive == 0) {
            game.randomize();
            cellCount();
            cellAlive.setText("Alive: " + cellsAlive);
            cellDead.setText("   Dead: " + cellsDead);
        } else {
            game.iterate();
            cellCount();
            cellAlive.setText("Alive: " + cellsAlive);
            cellDead.setText("   Dead: " + cellsDead);
        }
    }

    private void resetRespond(MouseEvent e) {
        game.randomize();
        cellCount();
        cellAlive.setText("Alive: " + cellsAlive);
        cellDead.setText("   Dead: " + cellsDead);
    }

    private void cellRespond(MouseEvent e) {
        Cell cell = (Cell) e.getSource();
        if (cell.isAlive()) {
            cell.setAlive(false);
            cellsAlive--;
            cellsDead++;
        } else if (!cell.isAlive()) {
            cell.setAlive(true);
            cellsAlive++;
            cellsDead--;
        }
        cell.updateColors();

        cellAlive.setText("Alive: " + cellsAlive);
        cellDead.setText("   Dead: " + cellsDead);
    }

    /**
     * Goes through the list of list of cells
     * and counts the number of cells that are alive and dead.
     */
    public void cellCount() {
        cellsDead = 0;
        cellsAlive = 0;
        for (int i = 0; i < game.getNumberOfCellsHigh(); i++) {  // the position in the column.
            for (int x = 0; x < game.getNumberOfCellsWide(); x++) { // the position in the row.
                if (!game.getCells().get(x).get(i).isAlive()) {
                    cellsDead += 1;
                } else if (game.getCells().get(x).get(i).isAlive()) {
                    cellsAlive += 1;
                }
            }
        }
    }
}
