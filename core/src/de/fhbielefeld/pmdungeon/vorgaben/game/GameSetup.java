package de.fhbielefeld.pmdungeon.vorgaben.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainGameController;

/**
 * ApplicationListener that delegates to the MainGameController.
 * Just some setup.
 */
public class GameSetup extends Game {

    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainGameController(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
