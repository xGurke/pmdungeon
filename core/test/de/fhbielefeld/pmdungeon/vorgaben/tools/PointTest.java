package de.fhbielefeld.pmdungeon.vorgaben.tools;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("TestCase: Point")
public class PointTest {
    private final float x = 2.0f;
    private final float y = 4.5f;

    //-----------constructor--------------
    @DisplayName("constructor: via floats")
    @Test
    void testConstructorFloats() {
        Point point = new Point(x, y);

        assertEquals(point.x, x, "x position must be the same");
        assertEquals(point.y, y, "y position must be the same");
    }

    @DisplayName("constructor: via Coordinate")
    @Test
    void testConstructorCoordinate() {
        final int ix = (int) x * 10;
        final int iy = (int) y * 10;
        Coordinate coordinate = new Coordinate(ix, iy);

        Point point = new Point(coordinate);

        assertEquals(point.x, ix, "x position must be the same");
        assertEquals(point.y, iy, "y position must be the same");
    }

    @DisplayName("constructor: via Point")
    @Test
    void testConstructorPoint() {
        Point tmp = new Point(x, y);

        Point point = new Point(tmp);

        assertEquals(point.x, x, "x position must be the same");
        assertEquals(point.y, y, "y position must be the same");
    }

}
