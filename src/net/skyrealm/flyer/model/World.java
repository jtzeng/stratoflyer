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

package net.projectrainbow.slick.flyer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * World.java
 * @author justin.zeng1
 *
 */

public class World {
	
	private static final World INSTANCE = new World();
	
	private final List<Bullet> bullets = new ArrayList<Bullet>();
	private final List<Entity> dots = new ArrayList<Entity>();
	private final List<Star> stars = new ArrayList<Star>();
	private final List<Bullet> starBullets = new ArrayList<Bullet>();
	
	public List<Bullet> getBullets() {
		return bullets;
	}

	public List<Entity> getDots() {
		return dots;
	}

	public List<Star> getStars() {
		return stars;
	}

	public List<Bullet> getStarBullets() {
		return starBullets;
	}
	
	private World() {
		// to prevent instantiation.
	}
	
	public static World getWorld() {
		return INSTANCE;
	}

}
