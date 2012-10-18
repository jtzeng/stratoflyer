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

package net.skyrealm.flyer.game;

import static net.skyrealm.flyer.Constants.HEIGHT;
import static net.skyrealm.flyer.Constants.MAX_DOTS;
import static net.skyrealm.flyer.Constants.STAR_DIST_X;
import static net.skyrealm.flyer.Constants.STAR_DIST_Y;
import static net.skyrealm.flyer.Constants.WIDTH;

import org.newdawn.slick.GameContainer;

import net.skyrealm.flyer.model.Player;
import net.skyrealm.flyer.stage.GameStage;
import net.skyrealm.flyer.util.ScriptManager;

public class Init {
	
	/**
	 * Called at the start of the game.
	 * @param container
	 */
	public static void init(GameContainer container) {
		/*
		 * Clears all entities in the World except for the player.
		 */
		ScriptManager.executeFunction("game_init.rb", "clear_all_entities");

		/*
		 * Initializes the images.
		 */
		ScriptManager.executeFunction("game_init.rb", "init_images");

		/*
		 * Initializes the player.
		 */
		Game.getInstance().setPlayer((Player) ScriptManager.executeFunction("game_init.rb",
				"init_player", WIDTH, HEIGHT));

		/*
		 * Sets the stage.
		 */
		Game.getInstance().setStage(GameStage.GAME_PLAY);

		/*
		 * Spawning the stars.
		 */
		ScriptManager.executeFunction("game_init.rb", "spawn_stars",
				STAR_DIST_X, STAR_DIST_Y, 0.4f, Game.getInstance().starImage);

		/*
		 * Initiating default direction of the stars.
		 */
		ScriptManager.executeFunction("game_init.rb", "set_default_star_dir");

		/*
		 * Spawning the initial dots.
		 */
		ScriptManager.executeFunction("game_init.rb", "spawn_init_dots",
				MAX_DOTS / 2);

	}
}
