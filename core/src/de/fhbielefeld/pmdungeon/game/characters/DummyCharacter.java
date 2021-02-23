package de.fhbielefeld.pmdungeon.game.characters;

import de.fhbielefeld.pmdungeon.game.GameWorld;

public class DummyCharacter extends Character{


   public DummyCharacter(GameWorld world){
        super(world,1,1);
    }
    @Override
    public float getPositionY(){ return 10;}

    @Override
    public float getPositionX(){ return 10;}

    @Override
    protected Animation setupIdleAnimation() {
        return null;
    }

    @Override
    protected Animation setupRunAnimation() {
        return null;
    }
}
