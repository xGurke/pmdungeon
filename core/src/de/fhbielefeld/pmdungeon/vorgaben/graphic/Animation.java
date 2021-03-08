package de.fhbielefeld.pmdungeon.vorgaben.graphic;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * A set of textures to build an animation.
 * 
 * @author Andre Matutat
 *
 */
public class Animation {

	// The set of textures that build the animation.
	private final ArrayList<Texture> animationFrames;
	// The count of textures for the animation
	private final int frames;
	// The count that represents the index of the NEXT texture that will be returned
	private int currentFrameIndex = 0;

	/**
	 * Creates an animation
	 * 
	 * @param animationFrames The list of textures that builds the animation. Must
	 *                        be in order.
	 */
	public Animation(ArrayList<Texture> animationFrames) {
		if (animationFrames.isEmpty())
			throw new IllegalArgumentException("An animation must have at least 1 frame");
		this.animationFrames = animationFrames;
		this.frames = animationFrames.size() - 1;
	}

	/**
	 * 
	 * @return the number of frames that this animation has.
	 */
	public int getAnimationLength() {
		return this.frames;
	}

	/**
	 * @return the texture of the current animation step (draw this)
	 */
	public Texture getCurrentAnimationTexture() {
		int returnFrame = currentFrameIndex;
		// after the last frame is returned, go back to the first frame
		currentFrameIndex = (currentFrameIndex + 1) % frames;
		return animationFrames.get(returnFrame);

	}

}
