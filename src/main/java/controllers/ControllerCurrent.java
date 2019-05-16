package controllers;
/*-
 * #%L
 * Winery
 * %%
 * Copyright (C) 2019 Faculty of Informatics, University of Debrecen
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Bottled;
import models.BottledDao;
import models.Containers;
import models.ContainersDao;
import org.pmw.tinylog.Logger;

import java.io.IOException;

import static java.lang.System.exit;


/**
 * A controller for the CurrentStatus.fxml file.
 */
public class ControllerCurrent {
    //CHECKSTYLE:OFF

    public ImageView StatusBackground;
    DatabaseManage db=new DatabaseManage();
    private BottledDao bottledDao = db.getBottledDao();
    private ContainersDao containersDao = db.getContainersDao();

    private ObservableList BottledList;
    private ObservableList ContainerList;
    private void Initialize() {

        BottledList = FXCollections.observableArrayList(bottledDao.findAll());
        ContainerList = FXCollections.observableArrayList(containersDao.findAll());
        Logger.info("ObservableArraylists initialized.");

    }



    public Button Back;
    @FXML
    TableView<Containers> ContainerTable = new TableView<>();
    @FXML
    TableView<Bottled> BottledTable = new TableView<>();


    @FXML
    TableColumn<Containers,Integer> Identity = new TableColumn<>("Id");
    @FXML
    TableColumn<Containers,Integer> Container= new TableColumn<>("Container");
    @FXML
    TableColumn<Containers, String> Type = new TableColumn<>("Type");
    @FXML
    TableColumn<Containers,Integer> Amount = new TableColumn<>("Amount");
    @FXML
    TableColumn<Containers,String> WineID = new TableColumn<>("WineId");
    @FXML
    TableColumn<Bottled,String> WineIdBottl = new TableColumn<>("WineId");
    @FXML
    TableColumn<Bottled,Integer> AmountBottle= new TableColumn<>("Amount");
    @FXML
    TableColumn<Bottled,Integer> OnStock= new TableColumn<>("OnStock");
    @FXML
    TableColumn<Containers,Integer> Sold = new TableColumn<>("Sold");
    @FXML
    TableColumn<Containers,String>  Recipients = new TableColumn<>("Recipients");


    /**
     * Displays the 2 tables.
     */
    @FXML
    public void Show() {

        Logger.info("Showing Tables...");
        Initialize();

        Container.setCellValueFactory(new PropertyValueFactory<>("Container"));
        Identity.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        WineID.setCellValueFactory(new PropertyValueFactory<>("WineId"));
        WineIdBottl.setCellValueFactory(new PropertyValueFactory<>("WineId"));
        AmountBottle.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        OnStock.setCellValueFactory(new PropertyValueFactory<>("OnStock"));
        Sold.setCellValueFactory(new PropertyValueFactory<>("Sold"));
        Recipients.setCellValueFactory(new PropertyValueFactory<>("Recipient"));



        ContainerTable.setItems(ContainerList);
        BottledTable.setItems(BottledList);




    }

    /**
     * Handles pressing the Back button.
     */
    public void HandleBack() {
        try {
            Logger.info("Back button pressed");
            Stage stage = (Stage) Back.getScene().getWindow();
            FXMLLoader fl = new FXMLLoader(getClass()
                    .getResource("/fxml/Main.fxml"));
            Parent root = fl.load();
            fl.<Controller>getController().setimage();
            stage.setTitle("Winery Manager");
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("/styles/ExamStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            stage.setOnCloseRequest(e -> exit(0));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles pressing the New Entry button.
     */
    public void handleNew() {


        try {
            Logger.info("New Entry button pressed!");
            Stage stage = (Stage) Back.getScene().getWindow();
            FXMLLoader fl = new FXMLLoader(getClass()
                    .getResource("/fxml/NewEntry.fxml"));
            Parent root = fl.load();
            fl.<ControllerNew>getController().setimage();
            stage.setTitle("New Entry");
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("/styles/ExamStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            stage.setOnCloseRequest(e -> exit(0));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
//CHECKSTYLE:ON

    /**
     * Sets the background image.
     */
    public void setimage(){
        StatusBackground.setImage(new Image(getClass().getClassLoader().
                getResourceAsStream("images/Grapes.jpg")));
    }
}
