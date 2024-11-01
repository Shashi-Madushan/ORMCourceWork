package com.shashimadushan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));

            Scene scene = new Scene(loader.load());


            // Set the stage properties
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.setResizable(true);
            //primaryStage.setResizable(false);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
