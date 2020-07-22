/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers;

import com.jfoenix.controls.JFXButton;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import models.Clients;
import models.Providers;
import products.ProductsViewController;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class ProvidersViewController implements Initializable {

    @FXML
    private TableView<Providers> tableView;
    @FXML
    private TableColumn<Providers, String> IdCol;
    @FXML
    private TableColumn<Providers, String> NameCol;
    @FXML
    private TableColumn<Providers, String> RepresCol;
    @FXML
    private TableColumn<Providers, String> AdressCol;
    @FXML
    private TableColumn<Providers, String> CityCol;
    @FXML
    private TableColumn<Providers, String> PhoneCol;
    @FXML
    private TableColumn<Providers, String> FaxCol;
    @FXML
    private TableColumn<Providers, String> CnssCol;
    @FXML
    private TableColumn<Providers, String> ManageCol;
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

    ObservableList<Providers> providersList = FXCollections.observableArrayList();

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Providers provider;
    boolean update = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(ProvidersViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadData() throws SQLException {

        refreshTable();

        IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        RepresCol.setCellValueFactory(new PropertyValueFactory<>("RepresPr"));
        AdressCol.setCellValueFactory(new PropertyValueFactory<>("Adress"));
        CityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
        PhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        FaxCol.setCellValueFactory(new PropertyValueFactory<>("Fax"));
        CnssCol.setCellValueFactory(new PropertyValueFactory<>("Cnss"));

        // ManageCol.setCellValueFactory(new PropertyValueFactory("update"));
        // insert btn in every row
        Callback<TableColumn<Providers, String>, TableCell<Providers, String>> cellFoctory = (TableColumn<Providers, String> param) -> {

            // make the tablecell containing buttons 
            final TableCell<Providers, String> cell = new TableCell<Providers, String>() {

                // Override updateItem method 
                @Override
                public void updateItem(String item, boolean empty) {

                    super.updateItem(item, empty);
                    // ensure that cell is created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        final JFXButton editButton = new JFXButton("update");

                        editButton.setStyle("-fx-background-color:#2196F3;"
                                + "-fx-background-radius:4px;"
                                + "-fx-font-size: 8px; "
                                + "-fx-text-fill: #ffffff;"
                        );

                        final Button deleteButton = new Button("delete");

                        deleteButton.setStyle("-fx-background-color:#f44336;"
                                + "-fx-background-radius:4px;"
                                + "-fx-font-size: 8px; "
                                + "-fx-text-fill: #ffffff;"
                        );

                        deleteButton.setOnAction((ActionEvent event) -> {

                            try {

                                Providers provider = getTableView().getItems().get(getIndex());

                                query = "delete from FOURNISSEUR where IDFO = '" + provider.getId() + "'";
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();

                                connection.close();

                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(ProductsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        tableView.setStyle("-fx-alignment: CENTER-RIGHT;");
                        HBox managebtn = new HBox(editButton, deleteButton);
                        HBox.setMargin(deleteButton, new Insets(5, 8, 5, 3));
                        HBox.setMargin(editButton, new Insets(5, 3, 5, 10));

                        editButton.setOnAction(event -> {

                            update = true;
                            provider = getTableView().getItems().get(getIndex());
                            NameField.setText(provider.getName());
                            RepreField.setText(provider.getRepresPr());
                            AdressField.setText(provider.getAdress());
                            CityField.setText(provider.getCity());
                            PhoneField.setText(provider.getPhone());
                            FaxField.setText(provider.getFax());
                            CnssField1.setText(provider.getCnss());

                            System.out.println(provider.getId());

                        });

                        setGraphic(managebtn);

                        setText(null);

                    }

                }

            };

            return cell;

        };
        ManageCol.setCellFactory(cellFoctory);
        tableView.setItems(providersList);

    }

    @FXML
    private void getSelected(MouseEvent event) {
    }

    private void isRegister() {
        connection = DbConnect.getConnect();

        if (update == false) {

            query = "INSERT INTO `fournisseur` (NOFR,NORE, ADFR,VIFR, TEFR,FAFR,CNSS) VALUES (?, ?,?, ?,?, ?, ?)";

        } else {

            query = "UPDATE `FOURNISSEUR` SET `NOFR`=?,`NORE`=?,`"
                    + "ADFR`=?,`VIFR`=?,"
                    + "`TEFR`=?,"
                    + "`FAFR`=?,`CNSS`=? WHERE IDFO ='" + provider.getId() + "'";

        }
    }

    @FXML
    private void Registersign(ActionEvent event) {

        String name = NameField.getText();
        String representative = RepreField.getText();
        String adress = AdressField.getText();
        String city = CityField.getText();
        String phone = PhoneField.getText();
        String fax = FaxField.getText();
        String cnss = CnssField1.getText();

        if (name.isEmpty() || representative.isEmpty() || adress.isEmpty()
                || city.isEmpty() || phone.isEmpty()
                || fax.isEmpty() || cnss.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
            return;
        } else {
            isRegister();
            insert();
            clean();

        }
    }

    private void refreshTable() {

        providersList.clear();

        try {
            connection = DbConnect.getConnect();
            query = "SELECT * FROM `fournisseur`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                providersList.add(new Providers(
                        resultSet.getInt("IDFO"),
                        resultSet.getString("NOFR"),
                        resultSet.getString("NORE"),
                        resultSet.getString("ADFR"),
                        resultSet.getString("VIFR"),
                        resultSet.getString("TEFR"),
                        resultSet.getString("FAFR"),
                        resultSet.getString("CNSS"))
                );
                tableView.setItems(providersList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.err.print(e);
        }

    }

    private void insert() {
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, NameField.getText());
            preparedStatement.setString(2, RepreField.getText());
            preparedStatement.setString(3, AdressField.getText());
            preparedStatement.setString(4, CityField.getText());
            preparedStatement.setString(5, PhoneField.getText());
            preparedStatement.setString(6, FaxField.getText());
            preparedStatement.setString(7, CnssField1.getText());

            preparedStatement.execute();
            refreshTable();
            //JOptionPane.showMessageDialog(null, "succes");
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    private void clean() {
    }

}
