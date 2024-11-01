package com.shashimadushan.utils;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ButtonUtils {

    public static void setupButtonAnimation(JFXButton button) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), button);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), button);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> scaleUp.playFromStart());
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> scaleDown.playFromStart());
    }

    public static void setupButtonClick(JFXButton button) {
            clearSelection(button.getParent());
            button.getStyleClass().add("selected");
    }

    private static void clearSelection(Parent parent) {
        if (parent instanceof Pane) {
            Pane pane = (Pane) parent;
            for (Node node : pane.getChildren()) {
                if (node instanceof JFXButton) {
                    ((JFXButton) node).getStyleClass().remove("selected");
                }
            }
        }
    }
}