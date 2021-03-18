package de.fhbielefeld.pmdungeon.vorgaben;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class TollesHudElement implements IHUDElement {
    @Override
    public Point getPosition() {
        return new Point(5,5);
    }

    @Override
    public Texture getTexture() {
        return new Texture("textures/chest/chest_full_open_anim_f0.png");
    }
}
