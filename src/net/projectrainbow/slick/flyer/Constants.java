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

package net.projectrainbow.slick.flyer;

/**
 * Constants.java
 * @author justin.zeng1
 * 
 * Stores game constants.
 * >inb4s1gma.
 *
 */

public interface Constants {
	/**
	 * The title of the game.
	 */
	static final String TITLE = "StratoFlyer";
	
	/**
	 * The version of the game (for the title).
	 */
	static final String VERSION = "2.3";
	
	/**
	 * The path to the images/resources.
	 */
	static final String PATH = "./images/";
	
	/**
	 * The FPS rate which the game runs in.
	 */
	static final int FPS_RATE = 60;
	
	/**
	 * The dimensions of the game.
	 */
	static final int WIDTH = 512;
	static final int HEIGHT = 512;
	
	/**
	 * The max speed of the player in pixels.
	 */
	static final int MAX_SPEED = 2;
	
	/**
	 * The maximum amount of dots at one time.
	 */
	static final int MAX_DOTS = 25;
	
	/**
	 * The frequency of dot generation.
	 * The higher the value, the less frequent dots appear.
	 */
	static final int DOT_OCC_RATE = 20;
	
	/**
	 * The speed of the dots.
	 */
	static final int DOT_SPEED = 1;
	
	/**
	 * Dot size (in pixels).
	 */
	static final int DOT_SIZE = 4;
	
	/**
	 * The speed of the bullets.
	 */
	static final int BULLET_SPEED = 3;
	
	/**
	 * The width of the bullet.
	 */
	static final int BULLET_WIDTH = 8;
	
	/**
	 * The height of the bullet.
	 */
	static final int BULLET_HEIGHT = 16;
	
	/**
	 * The pixel movement speed of the bad stars.
	 */
	static final int STAR_SPEED = 2;
	
	/**
	 * The interval at which the player is
	 * allowed to shoot a bullet (in ms).
	 */
	static final int SHOOT_INTERVAL = 300;
	
	/**
	 * The player score multiplier.
	 */
	static final int SCORE_MULTIPLIER = 1000;
	
	/**
	 * The player's default health.
	 */
	static final int PLR_DEFAULT_HEALTH = 10;
	
	/**
	 * The occurance at which the stars
	 * decide to shoot a bullet. The
	 * higher the number, the less frequently
	 * the star will decide to shoot.
	 */
	static final int STAR_SHOOT_OCC = 1000;
	
	static final int STAR_DIST_X = 25;
	
	static final int STAR_DIST_Y = 15;
}
