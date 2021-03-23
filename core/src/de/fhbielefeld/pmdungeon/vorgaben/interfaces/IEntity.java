package de.fhbielefeld.pmdungeon.vorgaben.interfaces;

/**
 * Must be implemented for all objecets that should be controlled by the DungeonEntityController
 */
public interface IEntity {

    /**
     * Will be executed every frame. Remember to draw/animate your drawable objects.
     */
    public void update();

    /**
     * @return if this instance can be deleted (than will be removed from DungeonEntityController list);
     */
    public boolean deleteable();

}
