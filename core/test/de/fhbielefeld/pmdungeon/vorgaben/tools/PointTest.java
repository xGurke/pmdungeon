package de.fhbielefeld.pmdungeon.vorgaben.tools;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("TestCase: Point")
public class PointTest {
    private final float x = 0.42f;
    private final float y = 0.69f;

    //-----------constructor--------------
    @DisplayName("float constructor")
    @Test
    void floatConstructor() {
        Point point = new Point(x, y);
        assertEquals(point.x, x);
        assertEquals(point.y, y);
    }

    @DisplayName("coordinate constructor")
    @Test
    void CoordinateConstructor() {
        final int ix = (int) x * 100;
        final int iy = (int) y * 100;
        Coordinate coordinate = new Coordinate(ix, iy);

        Point point = new Point(coordinate);

        assertEquals(point.x, ix);
        assertEquals(point.x, iy);
    }

    @DisplayName("point constructor")
    @Test
    void PointConstructor() {
        Point tmp = new Point(x, y);
        Point point = new Point(tmp);

        assertEquals(point.x, x);
        assertEquals(point.y, y);
    }

}
