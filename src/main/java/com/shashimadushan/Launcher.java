package com.shashimadushan;

import com.shashimadushan.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

            Scene scene = new Scene(loader.load());
            LoginController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(scene);
            stage.setTitle("Login");
            stage.setResizable(false);


            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
