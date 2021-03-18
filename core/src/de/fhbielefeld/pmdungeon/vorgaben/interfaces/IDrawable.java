package de.fhbielefeld.pmdungeon.vorgaben.interfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
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
	 */
	default public void draw(float xOffset, float yOffset) {
		Texture texture=this.getTexture();
		Sprite sprite = new Sprite(texture);
		//this will resize the texture. this is setuped for the textures used in the thesis
		sprite.setSize(1, ((float) texture.getHeight() / (float) texture.getWidth()));
		//where to draw the sprite
		sprite.setPosition(this.getPosition().x+xOffset, this.getPosition().y+yOffset);

		//need to be called before drawing
		GameSetup.batch.begin();
		//draw sprite
		sprite.draw(GameSetup.batch);
		//need to be called after drawing
		GameSetup.batch.end();
	}

	/**
	 * Draws the instance based on its position with default offset.
	 */
	default public void draw(){
		//found offset by try and error
		this.draw(-0.85f,-0.5f);
	}


}
