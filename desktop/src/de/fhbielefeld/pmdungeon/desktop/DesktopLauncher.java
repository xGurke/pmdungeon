package de.fhbielefeld.pmdungeon.desktop;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;

public class DesktopLauncher {


    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(Constants.WINDOWNAME);
        config.setForegroundFPS(Constants.FRAMERATE);
        config.setWindowedMode(Constants.WIDTH,Constants.HEIGHT);
        config.setResizable(false);
        new Lwjgl3Application(new GameSetup(), config);
    }
}
