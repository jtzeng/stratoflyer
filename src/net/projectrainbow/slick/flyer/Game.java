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

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.projectrainbow.slick.flyer.model.Bullet;
import net.projectrainbow.slick.flyer.model.Direction;
import net.projectrainbow.slick.flyer.model.Entity;
import net.projectrainbow.slick.flyer.model.Player;
import net.projectrainbow.slick.flyer.model.Star;
import net.projectrainbow.slick.flyer.stage.GameStage;
import net.projectrainbow.slick.flyer.util.Utils;
import net.projectrainbow.slick.flyer.model.World;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

/**
 * Main.java
 * @author Whackatre
 * 
 */

public class Game extends BasicGame implements Constants {

	/**
	 * The game container.
	 */
	private AppGameContainer container;

	private GameStage stage;

	private long updateTime = 0;
	private long drawTime = 0;

	private Player plr;
	private Image tankImage;
	private Image starImage;
	private Image winImage;
	private Image loseImage;

	private boolean canShoot = true;
	private long score;

	private static Game game;

	public static Game getInstance() {
		return game;
	}

	public static void setInstance(Game game) {
		Game.game = game;
	}

	/**
	 * 
	 * @param title
	 */
	public Game(String title) {
		super(title);
		new Timer().schedule(new PlayerShootTimer(), 0, SHOOT_INTERVAL);
	}

	/**
	 * Ran at the start of the game. Events such as loading, etc. goes here.
	 */
	@Override
	public void init(GameContainer container) throws SlickException {
		/* random(WIDTH), random(HEIGHT) */

		World.getWorld().getDots().clear();
		World.getWorld().getStars().clear();
		World.getWorld().getBullets().clear();
		World.getWorld().getStarBullets().clear();

		if (tankImage == null) {
			tankImage = new Image(PATH + "notsoenemyplane.gif");
			starImage = new Image(PATH + "Star_Ouro.gif").getScaledCopy(0.025f);
			winImage = new Image(PATH + "YOU WIN.png");
			loseImage = new Image(PATH + "YOU LOSE.png").getScaledCopy(0.5f);
		}

		plr = new Player(new Point((int) (WIDTH * 0.5), (int) (HEIGHT * 0.8)),
				tankImage.getWidth(), tankImage.getHeight(), Color.white);

		stage = GameStage.GAME_PLAY;

		/*
		 * Spawning the stars.
		 */
		for (int x = STAR_DIST_X; x < WIDTH - STAR_DIST_X; x++) {
			for (int y = STAR_DIST_X; y < (HEIGHT * 0.4f); y++) {
				if (x % (Utils.random(STAR_DIST_Y) + STAR_DIST_Y) == 0
						&& y % (Utils.random(STAR_DIST_Y) + STAR_DIST_Y) == 0) {
					World.getWorld()
							.getStars()
							.add(new Star(new Point(x, y),
									starImage.getWidth(), starImage.getHeight()));
				}
			}
		}

		/*
		 * Initiating default direction of the stars.
		 */
		for (Star s : World.getWorld().getStars()) {
			if (Utils.random(1) == 1) {
				s.setDirection(Direction.EAST);
			} else {
				s.setDirection(Direction.WEST);
			}
		}
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

	public Image getTankImage() {
		return tankImage;
	}

	public void setTankImage(Image tankImage) {
		this.tankImage = tankImage;
	}

	public Image getStarImage() {
		return starImage;
	}

	public void setStarImage(Image starImage) {
		this.starImage = starImage;
	}

	public Image getWinImage() {
		return winImage;
	}

	public void setWinImage(Image winImage) {
		this.winImage = winImage;
	}

	public Image getLoseImage() {
		return loseImage;
	}

	public void setLoseImage(Image loseImage) {
		this.loseImage = loseImage;
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
