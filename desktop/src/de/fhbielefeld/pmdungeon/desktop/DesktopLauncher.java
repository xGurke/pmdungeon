package de.fhbielefeld.pmdungeon.desktop;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;

public class DesktopLauncher {

    /**
     * Runs the dungeon.
     * @param mc MainController of the Dungeon
     */
    public static void run(MainController mc) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(Constants.WINDOWNAME);
        config.setForegroundFPS(Constants.FRAMERATE);
        config.setWindowedMode(Constants.WIDTH,Constants.HEIGHT);
        config.setResizable(false);
        new Lwjgl3Application(new GameSetup(mc), config);
    }
}
