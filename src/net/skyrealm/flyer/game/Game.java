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

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import net.skyrealm.flyer.model.Direction;
import net.skyrealm.flyer.model.Entity;
import net.skyrealm.flyer.model.Player;
import net.skyrealm.flyer.model.Star;
import net.skyrealm.flyer.model.World;
import net.skyrealm.flyer.stage.GameStage;
import net.skyrealm.flyer.util.ScriptManager;
import net.skyrealm.flyer.util.Utils;
import static net.skyrealm.flyer.Constants.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Game.java
 * 
 * @author Whackatre
 * 
 */

public class Game extends BasicGame {

	/**
	 * The game container.
	 */
	private AppGameContainer container;

	private GameStage stage;

	private long updateTime = 0;
	private long drawTime = 0;

	private Player plr;
	public Image tankImage;
	public Image starImage;
	public Image winImage;
	public Image loseImage;

	private boolean canShoot = true;
	private long score;

	/**
	 * The instance of this Game.
	 */
	private static final Game instance = new Game(TITLE + " v[" + VERSION + "]");

	/**
	 * Gets the current instance
	 * (singleton) of this Game.
	 * 
	 * @return
	 */
	public static Game getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param title
	 */
	private Game(String title) {
		super(title);
		new Timer().schedule(new PlayerShootTimer(), 0, SHOOT_INTERVAL);
	}

	/**
	 * Ran at the start of the game. Events such as loading, etc. goes here.
	 */
	@Override
	public void init(GameContainer container) throws SlickException {

		/*
		 * Clears all entities in the World
		 * except for the player.
		 */
		ScriptManager.executeFunction("game_init.rb", "clear_all_entities");

		/*
		 * Initializes the images.
		 */
		ScriptManager.executeFunction("game_init.rb", "init_images");

		/*
		 * Initializes the player.
		 */
		plr = (Player) ScriptManager.executeFunction("game_init.rb", "init_player", WIDTH, HEIGHT);

		/*
		 * Sets the stage.
		 */
		stage = GameStage.GAME_PLAY;

		/*
		 * Spawning the stars.
		 */
		ScriptManager.executeFunction("game_init.rb", "spawn_stars", STAR_DIST_X, STAR_DIST_Y, 0.4f, starImage);

		/*
		 * Initiating default direction of the stars.
		 */
		ScriptManager.executeFunction("game_init.rb", "set_default_star_dir");

		/*
		 * Spawning the initial dots.
		 */
		ScriptManager.executeFunction("game_init.rb", "spawn_init_dots", MAX_DOTS / 2);

	}

	/**
	 * The game drawing process. Called after each update.
	 */
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		long initTime = container.getTime();
		Renderer.render(container, g);
		drawTime = container.getTime() - initTime;
	}

	/**
	 * Updates variables, etc. Called before each draw.
	 */
	@Override
	public void update(GameContainer game, int arg1) throws SlickException {
		long initTime = container.getTime();
		Updater.update(game, arg1);
		updateTime = container.getTime() - initTime;
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		// stars.add(new Star(new Point(x, y), 50, 50));
		if (stage == GameStage.GAME_OVER || stage == GameStage.GAME_WIN) {
			try {
				container.reinit();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(int key, char c) {
	}

	public AppGameContainer getContainer() {
		return container;
	}

	public void setGameContainer(AppGameContainer container) {
		this.container = container;
	}

	public GameStage getStage() {
		return stage;
	}

	public void setStage(GameStage stage) {
		this.stage = stage;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(long drawTime) {
		this.drawTime = drawTime;
	}

	public boolean canShoot() {
		return canShoot;
	}

	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public Player getPlayer() {
		return plr;
	}

	/**
	 * A process called every X seconds.
	 */
	class PlayerShootTimer extends TimerTask {

		public PlayerShootTimer() {
			super();
		}

		@Override
		public void run() {
			canShoot = true;
			return;
		}

	}

}
