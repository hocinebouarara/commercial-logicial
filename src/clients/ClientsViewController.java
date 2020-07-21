/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.Clients;
import models.Products;
import products.ProductsViewController;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class ClientsViewController implements Initializable {

    @FXML
    private TableView<Clients> tableView;
    @FXML
    private TableColumn<Clients, String> IdCol;
    @FXML
    private TableColumn<Clients, String> NameCol;
    @FXML
    private TableColumn<Clients, String> AdressCol;
    @FXML
    private TableColumn<Clients, String> CityCol;
    @FXML
    private TableColumn<Clients, String> PhoneCol;
    @FXML
    private TableColumn<Clients, String> FaxCol;
    @FXML
    private TableColumn<Clients, String> ReprCol;
    @FXML
    private TableColumn<Clients, String> ManageCol;
    @FXML
    private TextField NameField;
    @FXML
    private TextField AdressField;
    @FXML
    private TextField CityField;
    @FXML
    private TextField PhoneField;
    @FXML
    private TextField FaxField;
    @FXML
    private TextField ReprField;
    
    ObservableList<Clients> clientssList = FXCollections.observableArrayList();

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Products client;
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
            Logger.getLogger(ClientsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void getSelected(MouseEvent event) {
    }

    @FXML
    private void isRegister(ActionEvent event) {
    }

    @FXML
    private void clean(MouseEvent event) {
    }
    
    
    private void loadData() throws SQLException {

        refreshTable();

        IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        AdressCol.setCellValueFactory(new PropertyValueFactory<>("Adress"));
         CityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
        PhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        FaxCol.setCellValueFactory(new PropertyValueFactory<>("Fax"));
        ReprCol.setCellValueFactory(new PropertyValueFactory<>("RepresCl"));
             
        // ManageCol.setCellValueFactory(new PropertyValueFactory("update"));
        // insert btn in every row
        Callback<TableColumn<Clients, String>, TableCell<Clients, String>> cellFoctory = (TableColumn<Clients, String> param) -> {

            // make the tablecell containing buttons 
            final TableCell<Clients, String> cell = new TableCell<Clients, String>() {

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

                                Clients client = getTableView().getItems().get(getIndex());

                                query = "delete from article where IDAR = '" + client.getId() + "'";
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
                           Clients client= getTableView().getItems().get(getIndex());
                            NameField.setText(client.getName());
                            AdressField.setText(client.getAdress());
                            CityField.setText(client.getCity());
                            PhoneField.setText(client.getPhone());
                            FaxField.setText(client.getFax());
                            ReprField.setText(client.getRepresCl());
                          

                        });

                        setGraphic(managebtn);

                        setText(null);

                    }

                }

            };

            return cell;

        };
        ManageCol.setCellFactory(cellFoctory);
        tableView.setItems(clientssList);

    }

    private void refreshTable() {
        
         clientssList.clear();

        try {
            connection = DbConnect.getConnect();
            query = "select * from CLIENT";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                clientssList.add(new Clients(
                        resultSet.getInt("IDCL"),
                        resultSet.getString("NOCL"), resultSet.getString("ADCL"),
                        resultSet.getString("VICL"), resultSet.getString("TECL"), resultSet.getString("FACL"),
                        resultSet.getString("NORECL"))
                );
                tableView.setItems(clientssList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.err.print(e);
        }

    }
    
}
