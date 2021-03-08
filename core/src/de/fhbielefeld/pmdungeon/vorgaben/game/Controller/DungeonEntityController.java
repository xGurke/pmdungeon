package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IUpdateable;

import java.util.ArrayList;


/**
 * Handles every entity and the level
 */
public class DungeonEntityController{

	private final SpriteBatch batch;
	private ArrayList<IUpdateable> dungeonEntitys;

	public DungeonEntityController(SpriteBatch batch) {
		this.batch = batch;
		this.dungeonEntitys=new ArrayList<IUpdateable>();
	}


	public void update() {
		for (IUpdateable obj: dungeonEntitys){
			if (obj.deleteable()) removeEntity(obj);
			else obj.update();
		}

	}

	public void addEntity (IUpdateable entity){
		if(!dungeonEntitys.contains(entity))
		this.dungeonEntitys.add(entity);
	}

	public void removeEntity (IUpdateable entity){
		if (dungeonEntitys.contains(entity))
			this.dungeonEntitys.remove(entity);
	}

	public SpriteBatch getBatch() {
		return batch;
	}



}
