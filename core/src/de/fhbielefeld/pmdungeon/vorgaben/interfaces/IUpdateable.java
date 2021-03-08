package de.fhbielefeld.pmdungeon.vorgaben.interfaces;

public interface IUpdateable {

    /**
     * @return if this instance can be deletet (removed from entitycontroll);
     */
    public boolean deleteable();

    /**
     * Will be executed every frame. Remember to draw/animate your drawable objects.
     */
    public void update();
}
