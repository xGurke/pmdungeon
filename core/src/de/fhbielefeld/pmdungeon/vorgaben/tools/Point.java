package de.fhbielefeld.pmdungeon.vorgaben.tools;

/**
 * For easy handling of positions in the dungeon. <br>
 * No getter needed. All attributes are public. <br>
 * Point.x to get x <br>
 * Point.y to get y <br>
 * 
 *
 */
public class Point {

	public int x;
	public int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
