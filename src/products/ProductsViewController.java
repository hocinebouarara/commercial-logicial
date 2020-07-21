/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import modules.Products;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class ProductsViewController implements Initializable {

    @FXML
    private TableView<Products> tableView;
    @FXML
    private TableColumn<Products, String> IdCol;
    @FXML
    private TableColumn<Products, String> RefCol;
    @FXML
    private TableColumn<Products, String> DesigCol;
    @FXML
    private TableColumn<Products, String> CatCol;
    @FXML
    private TableColumn<Products, String> QntCol;
    @FXML
    private TableColumn<Products, String> BuypriceCol;
    @FXML
    private TableColumn<Products, String> TotBuyCol;
    @FXML
    private TableColumn<Products, String> SalePriceCol;
    @FXML
    private TableColumn<Products, String> TotSaleCol;
    @FXML
    private TableColumn<Products, String> ManageCol;
    @FXML
    private TextField refField;
    @FXML
    private TextField idfield;
    @FXML
    private TextField desigField;
    @FXML
    private TextField catField;
    @FXML
    private TextField QntField;
    @FXML
    private TextField buyField;
    @FXML
    private TextField totbuyField;
    @FXML
    private TextField saleField;
    @FXML
    private TextField totsalField;

    ObservableList<Products> productsList = FXCollections.observableArrayList();

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Products product;
    boolean update = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            loadData();

        } catch (SQLException ex) {
            Logger.getLogger(ProductsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    int index = -1;

    @FXML
    private void getSelected(MouseEvent event) {
        int index = tableView.getSelectionModel().getSelectedIndex();

        if (index >= -1) {
            refField.setText(RefCol.getCellData(index));
            desigField.setText(DesigCol.getCellData(index));
            QntField.setText(QntCol.getCellData(index));
            catField.setText(CatCol.getCellData(index));
            buyField.setText(BuypriceCol.getCellData(index));
            saleField.setText(SalePriceCol.getCellData(index));
            totbuyField.setText(TotBuyCol.getCellData(index));
            totsalField.setText(TotSaleCol.getCellData(index));

        }

    }

    private void loadData() throws SQLException {

        refreshTable();

        IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        RefCol.setCellValueFactory(new PropertyValueFactory<>("reference"));
        DesigCol.setCellValueFactory(new PropertyValueFactory<>("designation"));
        CatCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        QntCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        BuypriceCol.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        TotBuyCol.setCellValueFactory(new PropertyValueFactory<>("totalBuy"));
        SalePriceCol.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        TotSaleCol.setCellValueFactory(new PropertyValueFactory<>("totalSale"));
        //editableCols();

        // ManageCol.setCellValueFactory(new PropertyValueFactory("update"));
        // insert btn in every row
        Callback<TableColumn<Products, String>, TableCell<Products, String>> cellFoctory = (TableColumn<Products, String> param) -> {

            // make the tablecell containing buttons 
            final TableCell<Products, String> cell = new TableCell<Products, String>() {

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

                                Products product = getTableView().getItems().get(getIndex());

                                query = "delete from article where IDAR = '" + product.getId() + "'";
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
                            product = getTableView().getItems().get(getIndex());
                            refField.setText(product.getReference());
                            desigField.setText(product.getDesignation());
                            catField.setText(product.getCategory());
                            QntField.setText(Integer.toString(product.getQuantity()));
                            buyField.setText(Float.toString(product.getBuyingPrice()));
                            saleField.setText(Float.toString(product.getSalePrice()));
                            totbuyField.setText(Float.toString(product.getTotalBuy()));
                            totsalField.setText(Float.toString(product.getTotalSale()));

                        });

                        setGraphic(managebtn);

                        setText(null);

                    }

                }

            };

            return cell;

        };
        ManageCol.setCellFactory(cellFoctory);
        tableView.setItems(productsList);

    }

    @FXML
    public void isRegister(ActionEvent event) throws SQLException {
        connection = DbConnect.getConnect();

        if (update == false) {

            query = "INSERT INTO article (REFA, DEAR,QSAR, SEAR,PRAC,PRVE,PRTA,PRTV) VALUES ( ?,?, ?, ?, ?,?, ?, ?)";

        } else {

            query = "UPDATE `article` SET `REFA`=?,`"
                    + "DEAR`=?,`QSAR`=?,"
                    + "`SEAR`=?,"
                    + "`PRAC`=?,`PRVE`=?,`PRTA`=?,"
                    + "`PRTV`=? WHERE IDAR = '" + product.getId() + "'";
            

        }

        insert();
        clean();

    }

    public void Registersign() {

        String reference = refField.getText();
        String designation = desigField.getText();
        String category = catField.getText();
        String quantity = QntField.getText();
        String buyprice = buyField.getText();
        String totalbuy = TotBuyCol.getText();
        String saleprice = saleField.getText();
        String totalsales = totsalField.getText();

        if (reference.isEmpty() || designation.isEmpty()
                || category.isEmpty() || quantity.isEmpty()
                || buyprice.isEmpty() || totalbuy.isEmpty()
                || saleprice.isEmpty() || totalsales.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
            return;
        }

    }

    public void refreshTable() throws SQLException {
        productsList.clear();

        try {
            connection = DbConnect.getConnect();
            query = "select * from article";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productsList.add(new Products(
                        resultSet.getInt("IDAR"),
                        resultSet.getString("REFA"), resultSet.getString("DEAR"),
                        resultSet.getInt("QSAR"), resultSet.getString("SEAR"), resultSet.getFloat("PRAC"),
                        resultSet.getFloat("PRVE"), resultSet.getFloat("PRTA"), resultSet.getFloat("PRTV"))
                );
                tableView.setItems(productsList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.err.print(e);
        }

    }

   

    public void insert() {

        try {
            Registersign();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, refField.getText());
            preparedStatement.setString(2, desigField.getText());
            preparedStatement.setString(3, QntField.getText());
            preparedStatement.setString(4, catField.getText());
            preparedStatement.setString(5, buyField.getText());
            preparedStatement.setString(6, saleField.getText());
            preparedStatement.setString(7, totbuyField.getText());
            preparedStatement.setString(8, totsalField.getText());

            preparedStatement.execute();
            refreshTable();
            //JOptionPane.showMessageDialog(null, "succes");
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void clean() {
        refField.setText(null);
        desigField.setText(null);
        QntField.setText(null);
        catField.setText(null);
        QntField.setText(null);
        buyField.setText(null);
        totbuyField.setText(null);
        saleField.setText(null);
        totsalField.setText(null);
        update=false;
    }

}
