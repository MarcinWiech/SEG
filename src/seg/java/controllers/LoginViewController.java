//package seg.java.controllers;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//import seg.java.XMLLoader;
//
//import java.net.URL;
//import java.util.HashMap;
//import java.util.ResourceBundle;
//
//public class LoginViewController implements Initializable {
//
//    private HashMap<String,String> loginsAndPasswords = new HashMap<>();
//
//    public TextField loginTextField;
//    public PasswordField passwordTextField;
//    private XMLLoader xmlReaderDOM;
//
//    public LoginViewController() {
//
//        loginsAndPasswords.put("ctw", "ctw");
//        loginsAndPasswords.put("owner", "owner");
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//    }
//
//    public void login(ActionEvent actionEvent){
//
//
//        //check if the login and password are typed in
//        if (loginTextField.getText().isEmpty()) {
//
//            new Alert(Alert.AlertType.ERROR, "Login seems to be missing").showAndWait();
//            return;
//        }
//        else if(passwordTextField.getText().isEmpty()){
//
//            new Alert(Alert.AlertType.ERROR, "Password seems to be missing").showAndWait();
//            return;
//        }
//
//
//        String login = loginTextField.getText();
//        String password = passwordTextField.getText();
//
//
//        //for now hard coded
//        //check if login and password match
//        // We should probably use salt / hashing in the future.
//        // Maybe a db store too.
//        if(!authorise(login,password)){
//
//            new Alert(Alert.AlertType.ERROR, "Password or login is incorrect").showAndWait();
//        }
//        else{
//
//            if(login.equals("ctw")){
//                try {
//
//                    Stage stage = (Stage) passwordTextField.getScene().getWindow();
//                    stage.close();
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/dashboard.fxml"));
//                    Parent root1 = fxmlLoader.load();
//                    stage = new Stage();
//                    stage.setTitle("Dashboard");
//                    stage.setScene(new Scene(root1));
//                    stage.show();
//
//                    AirportOld airportOldToPass = xmlReaderDOM.getAirportArraylist().get("Heathrow");
//                    DashboardController dashboardController = fxmlLoader.getController();
//                    dashboardController.setValues(airportOldToPass);
//
//                } catch (Exception e) {
//
//                    System.out.println(e);
//                    new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
//                }
//            }
//            else{
//
//                try {
//
//                    Stage stage = (Stage) passwordTextField.getScene().getWindow();
//                    stage.close();
//
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/airportSelection.fxml"));
//                    Parent root1 = fxmlLoader.load();
//                    stage = new Stage();
//                    stage.setTitle("Select Airport");
//                    stage.setScene(new Scene(root1));
//                    stage.show();
//
//                } catch (Exception e) {
//
//                    System.out.println(e);
//                    new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
//                }
//
//            }
//        }
//
//
//
//    }
//
//    private boolean authorise(String login, String password){
//
//        return loginsAndPasswords.containsKey(login) && loginsAndPasswords.get(login).equals(password);
//    }
//}
