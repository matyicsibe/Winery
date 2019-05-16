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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Bottled;
import models.BottledDao;
import models.Containers;
import models.ContainersDao;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.lang.System.exit;
//CHECKSTYLE:OFF
public class ControllerNew {
    public ControllerNew() {
    }


    public ImageView NewBackground;
    public TextField BAtxt;

    public TextField BottlesTxt;
    public TextField Recipient;
    private DatabaseManage db = new DatabaseManage();
    private BottledDao bottledDao = db.getBottledDao();
    private ContainersDao containersDao = db.getContainersDao();


    public Button Back;
    public TextField ContainerTxt;
    public TextField TypeTxt;
    public TextField AmountTxt;
    public TextField WineIdTxt;
    public ChoiceBox<String> SellChoices= new ChoiceBox<>();
    public ChoiceBox<String> ChoiceWine = new ChoiceBox<>();
    private List<Containers> ContainerList = containersDao.findAll();
    private  List<Bottled> BottledList = bottledDao.findAll();


    void setChoice() {
        Logger.info("Setting up choiceboxes...");
        ObservableList<String> list1 = FXCollections.observableArrayList();
        ObservableList<String> list2 = FXCollections.observableArrayList();

        for (Containers containers : ContainerList) list1.add(containers.getWineId());
        for (Bottled bottled : BottledList) list2.add(bottled.getWineId());
        ChoiceWine.setItems(list1);
        SellChoices.setItems(list2);

    }


    public void HandleBack() {
        try {
            Logger.info("Back button pressed!");

            Stage stage = (Stage) Back.getScene().getWindow();
            FXMLLoader fl = new FXMLLoader(getClass()
                    .getResource("/fxml/Main.fxml"));
            Parent root = fl.load();
            fl.<Controller>getController().setimage();
            stage.setTitle("Winery controllers.Manager");
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



    public void checktexts(){
        if (db.isNotNumeric(ContainerTxt.getText())){
            Logger.warn("Cointainer must be a number!");
            ContainerTxt.clear();
            ContainerTxt.setPromptText("Enter a number!");
        }else {
            if (db.isNotNumeric(AmountTxt.getText())){
                Logger.warn("Amount must be a number!");
                AmountTxt.setPromptText("Enter a number!");
            }else{
                if (TypeTxt.getText() == null){
                    Logger.warn("Please enter a Type!");
                    AmountTxt.setPromptText("Enter a Type!");
                }else {
                    if (WineIdTxt.getText()==null){
                        Logger.warn("Amount must be a number!");
                        WineIdTxt.setPromptText("Enter a WineId!");
                    }else {
                        handleNew();
                    }
                }
            }
        }
    }

    private void handleNew() {

            Logger.info("New Entry button pressed!");

            Containers containers = db.handleNewRequest(AmountTxt.getText(),ContainerTxt.getText(),TypeTxt.getText(),WineIdTxt.getText());

            containersDao.persist(containers);
            ContainerTxt.clear();
            ContainerTxt.setPromptText("Number of Container");
            AmountTxt.clear();
            AmountTxt.setPromptText("null");
            TypeTxt.clear();
            WineIdTxt.clear();
            ContainerList = containersDao.findAll();
            setChoice();

            }




    public void handleBottling() {
        if (db.checkbottling(ChoiceWine.getValue(),BAtxt.getText())) {
            Logger.info("Bottling button pressed!");
            int Nothing = -1;

            for (Containers containers : ContainerList) {
                if (containers.getWineId().equals(ChoiceWine.getValue())) {
                    Nothing = containers.getId();
                    break;
                }
            }
            Optional resultbottl = bottledDao.find(ChoiceWine.getValue());
            if (resultbottl.isPresent()) {
                int a = Integer.parseInt(BAtxt.getText());
                double b = a / 0.75;
                Bottled bottle = (Bottled) resultbottl.get();
                Optional resultcont = containersDao.find(Nothing);
                if (resultcont.isPresent()) {
                    Containers cont = (Containers) resultcont.get();
                    bottle.setAmount(bottle.getAmount() + a);
                    bottle.setOnStock((int) (bottle.getOnStock() + b));
                    cont.setAmount(cont.getAmount() - a);
                    bottledDao.update(bottle);
                    if (cont.getAmount().equals(0)) {
                        containersDao.remove(cont);
                    } else {
                        containersDao.update(cont);
                    }
                }


            } else {
                int a = Integer.parseInt(BAtxt.getText());
                double b = a / 0.75;
                Optional resultcont = containersDao.find(Nothing);
                if (resultcont.isPresent()) {
                    Containers cont = (Containers) resultcont.get();
                    cont.setAmount(cont.getAmount() - a);
                    Bottled bottle = Bottled.builder()
                            .Amount(a)
                            .OnStock((int) b)
                            .Sold(0)
                            .WineId(cont.getWineId())
                            .Recipient("Not sold yet")
                            .build();
                    containersDao.update(cont);
                    bottledDao.persist(bottle);
                }
            }
            BAtxt.clear();
            ChoiceWine.setValue(null);
        }else{
            BAtxt.clear();
            ChoiceWine.setValue(null);
            BAtxt.setPromptText("Enter a Number!");
            ChoiceWine.setValue("Choose a Wine!");

        }

    }


    public void handleSell() {
        Optional result = bottledDao.find(SellChoices.getValue());

        int a = Integer.parseInt(BottlesTxt.getText());
        if (result.isPresent()) {
            Bottled bottled = (Bottled) result.get();
            if (bottled.getAmount() > a) {
                bottled.setOnStock(bottled.getOnStock() - a);
                bottled.setSold(bottled.getSold() + a);
                if (bottled.getRecipient().equals("Not sold yet")) bottled.setRecipient("");
                bottled.setRecipient(bottled.getRecipient() + Recipient.getText() + "(" + a + ")" + "\n");
                bottledDao.update(bottled);

                SellChoices.setValue(null);
                BottlesTxt.clear();
                Recipient.clear();
            }

        }
    }




    void setimage(){
        NewBackground.setImage(new Image(getClass().getClassLoader().
                getResourceAsStream("images/Storage.jpg")));
    }
    //CHECKSTYLE:ON

}
