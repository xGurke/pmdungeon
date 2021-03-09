package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IUpdateable;

import java.util.ArrayList;


/**
 * Handles every entity in the dungeon.
 */
public class DungeonEntityController {

    /**
     * Contains all the entitys this controller handles.
     */
    private ArrayList<IUpdateable> dungeonEntitys;

    public DungeonEntityController() {
        this.dungeonEntitys = new ArrayList<IUpdateable>();
    }

    /**
     * calls the update method for every entity in the list.
     * removes entity if deletable is set true
     */
    public void update() {
        for (IUpdateable obj : dungeonEntitys) {
            if (obj.deleteable()) removeEntity(obj);
            else obj.update();
        }
    }

    /**
     * add an entity to the list
     *
     * @param entity
     */
    public void addEntity(IUpdateable entity) {
        if (!dungeonEntitys.contains(entity))
            this.dungeonEntitys.add(entity);
    }

    /**
     * removes entity from the list
     *
     * @param entity
     */
    public void removeEntity(IUpdateable entity) {
        if (dungeonEntitys.contains(entity))
            this.dungeonEntitys.remove(entity);
    }

}
