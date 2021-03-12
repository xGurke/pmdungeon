package de.fhbielefeld.pmdungeon.vorgaben.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainGameController;

/**
 * ApplicationListener that delegates to the MainGameController.
 * Just some setup.
 */
public class GameSetup extends Game {

    /**
     * This INSTANCE is necessary to draw ALL the stuff. Every Object that uses draw need to know THIS INSTANCE
     */
    public static SpriteBatch batch;

    @Override
    public void create() {
        batch= new SpriteBatch();
        this.setScreen(new MainGameController(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
