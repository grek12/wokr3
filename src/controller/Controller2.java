package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Sotr;
import model.DAO1;



public class Controller2  {

    @FXML
    private TextField famField;

    @FXML
    private TextField okladField;

    @FXML
    private TextField otrDayField;
    @FXML
    private Controller mainController;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    @FXML
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (famField.getText() == null || famField.getText().length() == 0) {
            errorMessage += "Заполните поле фамилии сотрудника!\n";
        }
        if (okladField.getText() == null || okladField.getText().length() == 0) {
            errorMessage += "Заполните поле оклада!\n";
        }
        if (otrDayField.getText() == null || otrDayField.getText().length() == 0) {
            errorMessage += "Заполните поле отработанных дней!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Поля ввода не заполнены!");
            alert.setHeaderText("Заполните все поля ввода!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public void handleOk(javafx.event.ActionEvent event) {
        Controller controller = new Controller();
        Sotr sotr = new Sotr();
        if (isInputValid()) {

            sotr.setFam(famField.getText());
            sotr.setOklad(Double.parseDouble(okladField.getText()));
            sotr.setOtrday(Integer.parseInt(otrDayField.getText()));


            try {
                DAO1 dao = new DAO1();
                dao.insertClient(sotr);
                mainController.updateTable();
                controller.DialogInfo("Сотрудник успешно добавлен! ");



            }catch (Exception e){
                controller.DialogError("Не удалось добавить сотрудника!");

                e.printStackTrace();
            }

        }

        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }


}