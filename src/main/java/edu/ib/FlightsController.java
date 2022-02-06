package edu.ib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FlightsController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/Flights.fxml"));
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        RequestMaker test = new RequestMaker();
        System.out.println(test.getData()); // taki test czy połączenie działa
    }
}
