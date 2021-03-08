package de.fhbielefeld.pmdungeon.vorgaben.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;


/**
 * Should be implement by all objects that are drawable but have no animation
 *
 */
public interface IDrawable extends Disposable{
	
	/**
	 * @return  the exact position in the dungeon of this instance
	 * 
	 */
	public Point getPosition();
	
	/**
	 * @return the (current)texture of the object. 
	 */	
	public Texture getTexture(); 

	

	/**
	 * Draws the instance based on its position. 
	 * @param xOffset sometimes it can be helpful to use a small offset
	 * @param yOffset sometimes it can be helpful to use a small offset
	 * @param batch the global SpriteBatch that is used all over the dungeon 
	 */


	default public void draw(int xOffset, int yOffset, SpriteBatch batch) {	
		Texture texture=this.getTexture();
		Sprite sprite = new Sprite(texture);
		
		sprite.setSize(texture.getWidth(), texture.getHeight());		
		//ToDo 
		// getCharacterWidth allways returns 1. So what is happening here? 
		// sprite.setSize(character.getCharacterWidth(), ((float) texture.getHeight() / (float) texture.getWidth()) * character.getCharacterWidth());
			
		sprite.setPosition(this.getPosition().x+xOffset, this.getPosition().y+yOffset);
		sprite.draw(batch);
	}


}
