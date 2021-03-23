package de.fhbielefeld.pmdungeon.vorgaben;

import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;

/**
 * Point for own integration
 */
public abstract class DungeonIntegrator {

    /**
     * Will be called at the beginning of the program
     */
    public abstract void setup();

    /**
     * Will be called at the beginning of every frame
     */
    public abstract void beginFrame();

    /**
     * Will be called at the end of every frame
     */
    public abstract void endFrame();

    /**
     * Will be called if a new level gets loaded
     */
    public abstract void onLevelLoad();

    public abstract MainController getMainController();

}
