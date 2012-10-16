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

package net.skyrealm.flyer;

import static net.skyrealm.flyer.Constants.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Main.java
 * @author justin.zeng1
 *
 */

public class Main {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Game.setInstance(new Game(TITLE + " v[" + VERSION + "]"));
		try {
			/**
			 * Initializing the game.
			 */
			AppGameContainer container = new AppGameContainer(Game.getInstance(), WIDTH, HEIGHT, false);
			container.setAlwaysRender(true);
			container.setShowFPS(false);
			container.setTargetFrameRate(FPS_RATE);
			container.setVSync(true);
			
			//container.setClearEachFrame(false);
			
			Game.getInstance().setGameContainer(container);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
