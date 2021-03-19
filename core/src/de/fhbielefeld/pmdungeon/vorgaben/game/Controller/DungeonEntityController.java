package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Handles every entity in the dungeon.
 */
public class DungeonEntityController {

    /**
     * Contains all the entitys this controller handles.
     */
    private ArrayList<IEntity> dungeonEntitys;
    public DungeonEntityController() {
        this.dungeonEntitys = new ArrayList<IEntity>();
    }
    /**
     * calls the update method for every entity in the list.
     * removes entity if deletable is set true
     */
    public void update() {
        for (IEntity obj : dungeonEntitys) {
            if (obj.deleteable()) removeEntity(obj);
            else obj.update();
        }
    }
    /**
     * add an entity to the list
     *
     * @param entity
     */
    public void addEntity(IEntity entity) {
        if (!dungeonEntitys.contains(entity))
            this.dungeonEntitys.add(entity);
    }
    /**
     * removes entity from the list
     *
     * @param entity
     */
    public void removeEntity(IEntity entity) {
        if (dungeonEntitys.contains(entity))
            this.dungeonEntitys.remove(entity);
    }
    /**
     * returns entity list
     */
    public ArrayList<IEntity> getList(){ return this.dungeonEntitys;}
}
