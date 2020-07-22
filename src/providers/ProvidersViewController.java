/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class ProvidersViewController implements Initializable {

    @FXML
    private TableView<?> tableView;
    @FXML
    private TableColumn<?, ?> IdCol;
    @FXML
    private TableColumn<?, ?> NameCol;
    @FXML
    private TableColumn<?, ?> RepresCol;
    @FXML
    private TableColumn<?, ?> AdressCol;
    @FXML
    private TableColumn<?, ?> CityCol;
    @FXML
    private TableColumn<?, ?> PhoneCol;
    @FXML
    private TableColumn<?, ?> FaxCol;
    @FXML
    private TableColumn<?, ?> CnssCol;
    @FXML
    private TableColumn<?, ?> ManageCol;
    @FXML
    private TextField NameField;
    @FXML
    private TextField RepreField;
    @FXML
    private TextField AdressField;
    @FXML
    private TextField CityField;
    @FXML
    private TextField PhoneField;
    @FXML
    private TextField FaxField;
    @FXML
    private TextField CnssField1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void getSelected(MouseEvent event) {
    }

    @FXML
    private void Registersign(ActionEvent event) {
    }

    @FXML
    private void clean(MouseEvent event) {
    }
    
}
