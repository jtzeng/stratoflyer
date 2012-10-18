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
