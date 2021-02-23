package de.fhbielefeld.pmdungeon.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.fhbielefeld.pmdungeon.game.characters.Character;
import de.fhbielefeld.pmdungeon.game.characters.DummyCharacter;
import de.fhbielefeld.pmdungeon.game.dungeon.Dungeon;
import de.fhbielefeld.pmdungeon.game.interactable.Interactable;

import java.util.ArrayList;
import java.util.List;

/**
 * This is where all the strings run together. Handles every character, interactable und the dungeon itself.
 */
public class GameWorld implements Disposable {

    private final SpriteBatch batch;
    private final List<Character> characterList = new ArrayList<>();
    private final List<Interactable> interactables = new ArrayList<>();
    private Dungeon dungeon;
    private boolean nextLevelTriggered = false;
    private Character hero;
    public GameWorld(SpriteBatch batch) {
        this.batch = batch;
        hero=new DummyCharacter(this);
    }

    //todo place chest
    //todo setup player
    //todo populate dungeon




    /**
     * Setting up a new dungeon. Gets also called when the level changes and the dungeon gets replaced.
     *
     * @param dungeon new Dungeon
     */
    public void setupDungeon(Dungeon dungeon) {
        if (this.dungeon != null) this.dungeon.dispose();
        this.dungeon = dungeon;
        this.nextLevelTriggered = false;
        dungeon.makeConnections();
    }

    /**
     * Part of the gameloop. Updates everything in the dungeon, that needs to be updated.
     */
    public void update() {
        //todo activate if player is implemented
        //if (hero.currentTile() == dungeon.getNextLevelTrigger()) nextLevelTriggered = true;
        for (Interactable interactable : interactables) {
            interactable.update();
        }
        List<Character> deadCharacters = new ArrayList<>();
        for (Character character : characterList) {
            character.update();
            if (character.isDead()) deadCharacters.add(character);
        }
        characterList.removeAll(deadCharacters);
    }

    /**
     * Part of the gameloop. Renders everything in the dungeon and the dungeon itself.
     */
    public void render() {
        dungeon.renderFloor(batch);
        for (Interactable interactable : interactables) {
            interactable.render(batch);
        }

        dungeon.renderWalls(dungeon.getHeight() - 1, (int) getHero().getPositionY(), batch);

        for (Character character : characterList) {
            character.render();
        }
        dungeon.renderWalls((int) getHero().getPositionY(), 0, batch);
    }

    /**
     * Methode for finding the nearest interactable in the dungeon, from a given character.
     *
     * @param character Character who looks for an interactable
     * @return Nearest interactable to the character
     */
    public Interactable nearestInteractable(Character character) {
        float minDistance = Float.MAX_VALUE;
        Interactable returnInteractable = null;
        for (Interactable interactable : interactables) {
            float distance = character.distanceBetween(interactable.getPositionX(), interactable.getPositionY());
            if (minDistance > distance) {
                minDistance = distance;
                returnInteractable = interactable;
            }
        }
        return returnInteractable;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public List<Interactable> getInteractables() {
        return interactables;
    }

    public boolean isNextLevelTriggered() {
        return nextLevelTriggered;
    }

    public Character getHero(){
        return hero;
    }
    @Override
    public void dispose() {
        dungeon.dispose();
        for (Character character : characterList) {
            character.dispose();
        }
        for (Interactable interactable : interactables) {
            interactable.dispose();
        }
    }
}
