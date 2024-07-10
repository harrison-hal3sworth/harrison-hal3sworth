/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2023
 * Instructor: Prof. Brian King
 *
 * Name: Harrison Halesworth
 * Section: 02 - 10am
 * Date: 4/12/23
 * Time: 10:33 AM
 *
 * Project: csci205_final_project
 * Package: org.Duck.Model
 * Class: MenuModel
 *
 * Description:
 *
 * ****************************************
 */
package org.Duck.Model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.Duck.View.MenuView;

public class MenuModel {
    private final MenuView theView;

    public MenuModel(MenuView theView) {
        this.theView = theView;
    }

    public void enlargenLetters() {
        this.theView.getGameButton().setStyle(
                        "-fx-background-color: transparent;" +
                        " -fx-alignment: center; " +
                        "-fx-font-size: 30; " +
                        "-fx-font-family: 'M29_DUCK HOUND';"
        );
    }

}
