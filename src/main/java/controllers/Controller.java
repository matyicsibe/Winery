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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.pmw.tinylog.Logger;

import java.io.IOException;

import static java.lang.System.exit;
//CHECKSTYLE:OFF
public class Controller {


    public Button NewPressed;
    public Button Status;
    public ImageView MenuBackground;


    /**
     *Handles pressing the New Entry button.
     */
    @FXML
    public void New() {
        try {
            Logger.info("New Entry Button pressed!");
            Stage stage = (Stage) NewPressed.getScene().getWindow();
            FXMLLoader fl = new FXMLLoader(getClass()
                    .getResource("/fxml/NewEntry.fxml"));
            Parent root = fl.load();
            fl.<ControllerNew>getController().setimage();
            fl.<ControllerNew>getController().setChoice();
            stage.setTitle("New Entry");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            stage.setOnCloseRequest(e -> exit(0));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     *Handles Pressing the Current Status of the Storage Button.
     */
    public void Status() {

        try {
            Logger.info("Current Status button pressed!");

            Stage stage = (Stage) Status.getScene().getWindow();
            FXMLLoader fl = new FXMLLoader(getClass()
                    .getResource("/fxml/CurrentStatus.fxml"));
            Parent root = fl.load();
            fl.<ControllerCurrent>getController().Show();
            fl.<ControllerCurrent>getController().setimage();
            stage.setTitle("Current Status");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            stage.setOnCloseRequest(e -> exit(0));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setimage(){
        MenuBackground.setImage(new Image(getClass().getClassLoader().
                getResourceAsStream("images/Landscape.jpg")));
    }
    //CHECKSTYLE:ON
}