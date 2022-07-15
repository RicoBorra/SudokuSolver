package com.ricoborra.sudoku;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;



public class App extends Application {
    
    @Override
    public void start(Stage stage) {
        Button solveButton = new Button("Solve");
        Button clearButton = new Button("Clear");
        VBox group = new VBox();
        Label label = new Label();
        TextField[][] sudoku = new TextField[9][9];
        GridPane grigliona = new GridPane();
        Scene scene = new Scene(group);
        GridPane splash = new GridPane();
        Scene splashScreen = new Scene(splash);
        Label title = new Label("Sudoku Solver v0.1");
        Label subtitle = new Label("Written by RicoBorra");
        Button change = new Button("Let's go!");
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.NONE, null, new BorderWidths(10));
        
        change.setOnAction((ActionEvent eh) -> {
            stage.setScene(scene);
        });
        
        clearButton.setOnAction((ActionEvent eh) -> {
            for (int i=0; i<9; i++){
                for (int j=0; j<9; j++){
                    sudoku[i][j].setText("");
                    sudoku[i][j].setStyle("-fx-text-fill: black");
                }
            }
            label.setText("");
            label.setStyle("-fx-text-fill: black");
        });
        
        solveButton.setOnAction((ActionEvent eh) -> {
            int[][] base = new int[9][9];
            try {
                for (int i=0; i<9; i++){
                    for (int j=0; j<9; j++){
                        if (!sudoku[i][j].getText().equals("")){
                            base[i][j] = Integer.valueOf(sudoku[i][j].getText());
                        } else {
                            base[i][j] = 0;
                        }
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Errore conversione " + e);
            }
            Solver s = new Solver(base);
            if (!s.isValid()){
                label.setStyle("-fx-text-fill: red");
                label.setText("Non valido");
                return;
            } else {
                label.setText("Sto lavorando...");
            }
            int[][] soluz = s.solve();
            for (int i=0; i<9; i++){
                for (int j=0; j<9; j++){
                    if (sudoku[i][j].getText().equals("")){
                        sudoku[i][j].setText(Integer.toString(soluz[i][j]));
                        sudoku[i][j].setStyle("-fx-text-fill: orange");
                    }
                }
            }
            label.setText("Finito!");
            label.setStyle("-fx-text-fill: chartreuse");
        });
        
        for (int g=0; g<9; g++){
            GridPane griglia = new GridPane();
            griglia.setBorder(new Border(new BorderStroke(Color.BLACK, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            for (int y=1; y<=3; y++){
                for (int x=1; x<=3; x++){
                    TextField casella = new TextField();
                    casella.setPrefHeight(20);
                    casella.setPrefWidth(30);
                    casella.setAlignment(Pos.BASELINE_CENTER);
                    griglia.add(casella, x, y);
                    sudoku[x+3*(g%3) - 1][y+3*(g/3) - 1] = casella;
                }
            }
            grigliona.add(griglia, g%3 + 1, g/3 + 1);
        }
        
        
        group.setBorder(new Border(borderStroke));
        group.setSpacing(35.0);
        group.getChildren().add(grigliona);
        group.getChildren().add(solveButton);
        group.getChildren().add(label);
        group.getChildren().add(clearButton);
        title.setStyle("-fx-font-size: 30px");
        splash.add(title, 2, 2);
        splash.add(subtitle, 2, 3);
        splash.add(change, 5, 5);
        splash.setBorder(new Border(borderStroke));
        stage.setScene(splashScreen);
        stage.setTitle("Sudoku Solver");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}