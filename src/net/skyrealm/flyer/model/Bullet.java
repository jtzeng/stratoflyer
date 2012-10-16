/*
 * StratoFlyer is a simple 2D space shooter built for educational purposes.
 * Copyright (C) 2012	Justin Zeng (Whackatre)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.skyrealm.flyer.model;

import java.awt.Point;

import org.newdawn.slick.Color;

/**
 * Bullet.java
 * @author justin.zeng1
 * 
 * Represents the tank's bullet.
 *
 */

public class Bullet extends Entity {

	private Color color;
	private int width;
	private int height;
	
	/**
	 * 
	 * @param point
	 * @param width
	 * @param height
	 * @param color
	 */
	public Bullet(Point point, int width, int height, Color color) {
		super(point);
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
