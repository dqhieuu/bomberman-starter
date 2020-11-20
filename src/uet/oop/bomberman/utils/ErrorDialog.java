package uet.oop.bomberman.utils;

import javafx.scene.control.Alert;

public class ErrorDialog {
  public static void display(String title, String message) {
    Alert error = new Alert(Alert.AlertType.ERROR);
    error.setTitle("Lỗi");
    error.setHeaderText(title);
    error.setContentText(message);
    error.showAndWait();
  }

  public static void displayAndExit(String title, String message) {
    display(title, message);
    System.exit(0);
  }
}
