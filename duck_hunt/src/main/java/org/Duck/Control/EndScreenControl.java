/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2023
 * Instructor: Prof. Brian King
 *
 * Name: Harrison Halesworth
 * Section: 02 - 10am
 * Date: 4/19/23
 * Time: 10:30 AM
 *
 * Project: csci205_final_project
 * Package: org.Duck.Control
 * Class: EndScreenControl
 *
 * Description:
 *
 * ****************************************
 */
package org.Duck.Control;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import org.Duck.Model.MenuModel;
import org.Duck.View.EndScreenView;
import org.Duck.View.GameView;
import org.Duck.View.MenuView;

import java.util.Objects;

import static org.Duck.DuckMain.getPrimaryStage;

public class EndScreenControl {

    /** This will store the GameView of the application */
    private final GameView theGameView;

    private Application currentView;

    private final MenuView theView;

    /** This will store the GameControl of the application */
    private GameControl theGameController;

    /** This will store the MenuModel of the application*/
    private final MenuModel theModel;

    private final EndScreenView endScreenView;

    /**
     * Construct a controller that connects the model and the view for our
     * main menu
     *
     * @param theView
     * @param theGameView
     */
    public EndScreenControl(MenuView theView, MenuModel theModel, GameView theGameView, EndScreenView endScreenView) throws Exception {
        // Instantiate the fields of the MenuControl object
        this.theView = theView;
        this.theGameView = theGameView;
        this.theModel = theModel;
        this.endScreenView = endScreenView;
    }
}

