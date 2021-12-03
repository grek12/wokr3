package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Calculation;
import model.DAO1;
import model.Sotr;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

    @FXML
    private TableColumn<Sotr, String> fam;

    @FXML
    private TableColumn<Sotr, String> idSotr;

    @FXML
    private TableColumn<Sotr, Double> oklad;

    @FXML
    private TableColumn<Sotr, Integer> otrDay;

    @FXML
    private TableView<Sotr> sotrTable;
    @FXML
    private TableView<Calculation> sotrTable1;

    @FXML
    private TableColumn<Calculation, Double> tax;

    @FXML
    private TableColumn<Calculation, Double> vidach;
    @FXML
    private TableColumn<Calculation, Double> nachisleno;
    @FXML
    private TableColumn<Calculation, String> fam2;
    private Sotr sotr;
    public DAO1 dao;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = new DAO1();
        sotrTable.setItems(dao.selectSotr());
        sotrTable1.setItems(dao.selectSotrItog());
        idSotr.setCellValueFactory(new PropertyValueFactory<>("id"));
        fam.setCellValueFactory(new PropertyValueFactory<>("fam"));
        oklad.setCellValueFactory(new PropertyValueFactory<>("oklad"));
        otrDay.setCellValueFactory(new PropertyValueFactory<>("otrday"));
        fam2.setCellValueFactory(new PropertyValueFactory<>("fam"));
        nachisleno.setCellValueFactory(new PropertyValueFactory<>("nachisleno"));
        tax.setCellValueFactory(new PropertyValueFactory<>("tax"));
        vidach.setCellValueFactory(new PropertyValueFactory<>("vidach"));
    }



    @FXML
    private void addSotr() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/addSotr.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 410, 150);
            Stage stage = new Stage();
            stage.setTitle("Добавление нового сотрудника");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            Controller2 controller = fxmlLoader.getController();
            controller.setMainController(this);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }

    }








    @FXML
    private void updateSotr(Sotr sotr) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/updateSotr.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 410, 180);
            Stage stage = new Stage();
            stage.setTitle("Изменение данных сотрудника");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            Controller3 controller = fxmlLoader.getController();
            controller.setMainController(this);
            controller.setPerson(sotr);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }

    }



    @FXML
    private void deleteSotr() {
        Sotr sotr = sotrTable.getSelectionModel().getSelectedItem();
        if (sotr == null) {
            DialogError("Удаление невозможно, список сотрудников пуст!");
        } else {
            if (DialogConf("Подтверждаете удаление сотрудника?")) {

                try {

                    dao.deleteSotr(sotr.getId());
                    DialogInfo("Сотрудник успешно удален!");
                    updateTable();

                } catch (Exception e) {
                    DialogError("Ошибка при удалении");
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void updateTable() {
        sotrTable.setItems(dao.selectSotr());
        sotrTable1.setItems(dao.selectSotrItog());
    }




    public void DialogError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }


    public void DialogInfo(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(info);

        alert.showAndWait();
    }

    private boolean DialogConf(String conf) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(null);
        alert.setContentText(conf);

        Optional<ButtonType> opcao = alert.showAndWait();

        if (opcao.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }


    public void updateSotr(ActionEvent event) {
        Sotr sotr = sotrTable.getSelectionModel().getSelectedItem();
        if (sotr != null) {
            updateSotr(sotr);
        } else {
            DialogError("Сотрудник не выбран");
        }
    }
}



