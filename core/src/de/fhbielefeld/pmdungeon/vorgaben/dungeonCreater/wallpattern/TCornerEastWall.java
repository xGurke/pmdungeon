package de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.wallpattern;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.DungeonTextures;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.tiles.Tile;
public class TCornerEastWall extends WallPattern {

    public TCornerEastWall(ObjectMap<DungeonTextures, Texture> textureMap) {
        super(textureMap);

        this.patternList.add(new Tile.Type[][]{
                {A, W, A},
                {A, W, W},
                {A, W, A}
        });
    }

    @Override
    public void render(SpriteBatch batch, Coordinate position) {
        batch.draw(textureMap.get(DungeonTextures.WALL_MID), position.getX(), position.getY(), 1, 1);
        batch.draw(textureMap.get(DungeonTextures.WALL_CORNER_TOP_LEFT), position.getX(), position.getY() + 1f, 1, 1);
        batch.draw(textureMap.get(DungeonTextures.WALL_SIDE_MID_RIGHT), position.getX(), position.getY(), 1, 1);
        batch.draw(textureMap.get(DungeonTextures.WALL_SIDE_MID_RIGHT), position.getX(), position.getY() + 1f, 1, 1);
    }
}
