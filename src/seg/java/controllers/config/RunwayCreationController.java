package seg.java.controllers.config;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seg.java.models.Airport;
import seg.java.models.IllegalValueException;
import seg.java.models.Runway;

public class RunwayCreationController {
    public Button addRunwayButton;
    public Button backButton;
    public TextField ldaTextbox;
    public TextField asdaTextbox;
    public TextField todaTextbox;
    public TextField toraTextbox;
    public TextField runwayDesignatorTextbox;
    public TextField thresholdTextbox;

    private Airport airport;
    /**
     * WHEN ADD BUTTON IS PRESSED
     **/
    private double tora, toda, asda, lda, thres;
    private String designator;

    /**
     * WHEN BACK BUTTON IS PRESSED - GOES BACK TO ConfigureAirport
     **/
    public void backToConfigureAirport(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) asdaTextbox.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/config/airportConfig.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Configure Airport");
            stage.setScene(new Scene(root1));
            stage.show();

            AirportConfigurationController controller = fxmlLoader.getController();
            controller.setAirport(this.airport);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    public void addRunway(ActionEvent actionEvent) {
        try {


            if (runwayDesignatorTextbox.getText().isEmpty() || toraTextbox.getText().isEmpty() || todaTextbox.getText().isEmpty()
                    || asdaTextbox.getText().isEmpty() || ldaTextbox.getText().isEmpty() || thresholdTextbox.getText().isEmpty()) {
                throw new IllegalValueException("fields");

            }
            designator = runwayDesignatorTextbox.getText();


        } catch (IllegalValueException e) {
            return;
        }

        try {
            tora = Double.parseDouble(toraTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: TORA input!").showAndWait();
            return;
        }
        try {
            toda = Double.parseDouble(todaTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: TODA input!").showAndWait();
            return;
        }
        try {
            asda = Double.parseDouble(asdaTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: ASDA input!").showAndWait();
            return;
        }
        try {
            lda = Double.parseDouble(ldaTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: LDA input!").showAndWait();
            return;
        }
        try {
            thres = Double.parseDouble(thresholdTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: Displaced Threshold input!").showAndWait();
            return;
        }


        try {
            tora = Double.parseDouble(toraTextbox.getText());
            if (tora > 5600) {
                throw new IllegalValueException("largetora");
            } else if (tora < 100) {
                throw new IllegalValueException("smalltora");
            }

        } catch (IllegalValueException e) {
            return;
        }
        try {
            toda = Double.parseDouble(todaTextbox.getText());

            if (toda < tora) {
                throw new IllegalValueException("smalltoda");
            } else if (toda - tora > (tora / 2)) {
                throw new IllegalValueException("largeclearway");
            }

        } catch (IllegalValueException e) {
            return;
        }
        try {
            asda = Double.parseDouble(asdaTextbox.getText());
            if (tora > asda) {
                throw new IllegalValueException("smallasda");
            }

        } catch (IllegalValueException e) {
            return;
        }


        try {
            String name = runwayDesignatorTextbox.getText();
            Double tora = Double.parseDouble(toraTextbox.getText());
            Double toda = Double.parseDouble(todaTextbox.getText());
            Double asda = Double.parseDouble(asdaTextbox.getText());
            Double lda = Double.parseDouble(ldaTextbox.getText());
            Double threshold = Double.parseDouble(thresholdTextbox.getText());
            Runway runway = new Runway(name, null, tora, toda, asda, lda, threshold);
            airport.addRunway(runway);
            runwayDesignatorTextbox.clear();
            toraTextbox.clear();
            todaTextbox.clear();
            asdaTextbox.clear();
            ldaTextbox.clear();
            thresholdTextbox.clear();
        } catch (IllegalValueException e) {

        }

    }

    public void onFinish(ActionEvent actionEvent) throws IllegalValueException {

        try {


            if (runwayDesignatorTextbox.getText().isEmpty() || toraTextbox.getText().isEmpty() || todaTextbox.getText().isEmpty()
                    || asdaTextbox.getText().isEmpty() || ldaTextbox.getText().isEmpty() || thresholdTextbox.getText().isEmpty()) {
                throw new IllegalValueException("fields");

            }
            designator = runwayDesignatorTextbox.getText();


        } catch (IllegalValueException e) {
            return;
        }
        try {
            tora = Double.parseDouble(toraTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: TORA input!").showAndWait();
            return;
        }
        try {
            toda = Double.parseDouble(todaTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: TODA input!").showAndWait();
            return;
        }
        try {
            asda = Double.parseDouble(asdaTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: ASDA input!").showAndWait();
            return;
        }
        try {
            lda = Double.parseDouble(ldaTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: LDA input!").showAndWait();
            return;
        }
        try {
            thres = Double.parseDouble(thresholdTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: Displaced Threshold input!").showAndWait();
            return;
        }


        try {
            tora = Double.parseDouble(toraTextbox.getText());
            if (tora > 5600) {
                throw new IllegalValueException("largetora");
            } else if (tora < 100) {
                throw new IllegalValueException("smalltora");
            }

        } catch (IllegalValueException e) {
            return;
        }
        try {
            toda = Double.parseDouble(todaTextbox.getText());

            if (toda < tora) {
                throw new IllegalValueException("smalltoda");
            } else if (toda - tora > (tora / 2)) {
                throw new IllegalValueException("largeclearway");
            }

        } catch (IllegalValueException e) {
            return;
        }
        try {
            asda = Double.parseDouble(asdaTextbox.getText());
            if (tora > asda) {
                throw new IllegalValueException("smallasda");
            }

        } catch (IllegalValueException e) {
            return;
        }

        addRunway(actionEvent);

        try {
            Stage stage = (Stage) asdaTextbox.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/config/airportConfig.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Aiport Configuration");
            stage.setScene(new Scene(root1));
            stage.show();

            AirportConfigurationController controller = fxmlLoader.getController();
            controller.setAirport(this.airport);
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public void setRunway(Runway runway) {
        // Todo: Runway editing.
    }
}
