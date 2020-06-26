package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private TextField nameField;
    @FXML
    private Button helloButton;
    @FXML
    private Button byeButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label threadLabel;
    @FXML
    private TextArea outputArea;
    @FXML
    private CheckBox ourCheckBox;

    @FXML
    public void initialize() {
        disableButtons(true);
    }

    @FXML
    public void onButtonClicked(ActionEvent e) {
        String greeting = "";
        nameLabel.setText(this.nameField.getText());
        if (nameField.getText().equals("Ethan")) {
            nameLabel.setTextFill(Color.RED);
        } else {
            nameLabel.setTextFill(Color.BLACK);
        }
        threadLabel.setText("Starting Thread");
        Runnable task = () -> {
            try {
                String s = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
                System.out.println("I'm going to sleep on the: " + s);
                Thread.sleep(10000);
                Platform.runLater(() -> {
                    String s1 = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
                    System.out.println("I'm updating the label on the: " + s1);
                    threadLabel.setText("We did something!");
                });
            } catch(InterruptedException event) {
                // we don't care about this
            }
        };
        new Thread(task).start();

        if (e.getSource().equals(helloButton)) {
            greeting = "Hello, ";
        } else if (e.getSource().equals(byeButton)) {
            greeting = "Bye, ";
        }
        outputArea.setText(outputArea.getText() + greeting + this.nameField.getText() + "\n");
        if(ourCheckBox.isSelected()) {
            nameField.clear();
            disableButtons(true);
        }
    }

    @FXML
    public void handleKeyReleased(){
        String text = nameField.getText();
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
        disableButtons(disableButtons);
    }

    public void handleChange() {
        System.out.println("The checkbox is " + (ourCheckBox.isSelected() ? "checked" : "not checked"));
    }

    private void disableButtons(boolean isDisabled){
        helloButton.setDisable(isDisabled);
        byeButton.setDisable(isDisabled);
    }
}
