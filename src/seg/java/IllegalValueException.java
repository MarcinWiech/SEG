package seg.java;

import javafx.scene.control.Alert;

public class IllegalValueException extends IllegalArgumentException {


    public IllegalValueException(String s) {
        if (s.equals("negativeheight")) {
            new Alert(Alert.AlertType.ERROR, "The height can't have a negative value").showAndWait();
        } else if (s.equals("largeheight")) {
            new Alert(Alert.AlertType.ERROR, "The object can't have a height this large. Input a smaller value").showAndWait();
        } else if (s.equals("largetora")) {
            new Alert(Alert.AlertType.ERROR, "The runway cannot be this large. Input a smaller value for TORA").showAndWait();
        } else if (s.equals("smalltora")) {
            new Alert(Alert.AlertType.ERROR, "The runway can't be this small. Input a larger value for TORA").showAndWait();
        } else if (s.equals("fields")) {
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are filled!").showAndWait();
        } else if (s.equals("smalltoda")) {
            new Alert(Alert.AlertType.ERROR, "TODA can't be smaller than TORA").showAndWait();
        } else if (s.equals("smallasda")) {
            new Alert(Alert.AlertType.ERROR, "ASDA can't be smaller than TORA").showAndWait();
        } else if (s.equals("largeclearway")) {
            new Alert(Alert.AlertType.ERROR, "The clearway can't be larger than half of the runway. Input a smaller TODA or a larger TORA").showAndWait();
        }

    }

}