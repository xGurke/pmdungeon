package de.fhbielefeld.pmdungeon.vorgaben.interfaces;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;


/**
 * Should be implement by all hud objects
 */
public interface IHUDElement {

    /**
     * The position of hud elements are based on virtual coordinates.
     *
     * @return
     */
    public Point getPosition();

    public Texture getTexture();

    public default float getWidth() {
        return 0.5f;
    }

    public default float getHeight() {
        return getTexture().getHeight() / 2;
    }
}