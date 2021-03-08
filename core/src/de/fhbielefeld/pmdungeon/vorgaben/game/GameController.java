package de.fhbielefeld.pmdungeon.vorgaben.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.fhbielefeld.pmdungeon.vorgaben.dungeon.DungeonWorld;


/**
 * This is where all the strings run together. Handles every character,
 * interactable und the dungeon itself.
 */
public class GameController implements Disposable {

	private final SpriteBatch batch;
	private DungeonWorld dungeon;
	private boolean nextLevelTriggered = false;
	private Character hero;

	public GameController(SpriteBatch batch) {
		this.batch = batch;
	}

	/**
	 * Setting up a new dungeon. Gets also called when the level changes and the
	 * dungeon gets replaced.
	 *
	 * @param dungeon new Dungeon
	 */
	public void setupDungeon(DungeonWorld dungeon) {
		if (this.dungeon != null)
			this.dungeon.dispose();
		this.dungeon = dungeon;
		this.nextLevelTriggered = false;
		dungeon.makeConnections();
	}

	void update() {
	}

	/**
	 * Part of the gameloop. Renders everything in the dungeon and the dungeon
	 * itself.
	 */
	public void render() {
		dungeon.renderFloor(batch);
		dungeon.renderWalls(dungeon.getHeight() - 1, 0, batch);
		//dungeon.renderWalls(dungeon.getHeight() - 1, (int) getHero().getPositionY(), batch);
		//dungeon.renderWalls((int) getHero().getPositionY(), 0, batch);
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public DungeonWorld getDungeon() {
		return dungeon;
	}

	public boolean isNextLevelTriggered() {
		return nextLevelTriggered;
	}

	@Override
	public void dispose() {
		dungeon.dispose();
	}
}
