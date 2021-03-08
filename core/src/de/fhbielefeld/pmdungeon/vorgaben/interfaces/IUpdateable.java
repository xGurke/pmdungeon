package de.fhbielefeld.pmdungeon.vorgaben.interfaces;

/**
 * Must be implemented for all objecets that should be controlled by the DungeonEntityController
 */
public interface IUpdateable {

    /**
     * @return if this instance can be deletet (tahn will be removed from entityControll);
     */
    public boolean deleteable();

    /**
     * Will be executed every frame. Remember to draw/animate your drawable objects.
     */
    public void update();
}
