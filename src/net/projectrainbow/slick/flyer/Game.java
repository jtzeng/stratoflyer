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
	 * Ran at the start of the game.
	 * Events such as loading, etc. goes here.
	 */
	@Override
	public void init(GameContainer container) throws SlickException {
		/*random(WIDTH), random(HEIGHT)*/
		
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
		
		plr = new Player(new Point((int) (WIDTH * 0.5), (int) (HEIGHT * 0.8)), tankImage.getWidth(), tankImage.getHeight(), Color.white);

		
		stage = GameStage.GAME_PLAY;
		
		/*
		 * Spawning the stars.
		 */
		for (int x = 25; x < WIDTH - 25; x++) {
			for (int y = 25; y < (HEIGHT * 0.4f); y++) {
				if (x % (random(15) + 15) == 0 && y % (random(15) + 15) == 0) {
					World.getWorld().getStars().add(new Star(new Point(x, y), starImage.getWidth(), starImage.getHeight()));
				}
			}
		}
		
		/*
		 * Initiating default direction of the stars.
		 */
		for (Star s : World.getWorld().getStars()) {
			if (random(1) == 1) {
				s.setDirection(Direction.EAST);
			} else {
				s.setDirection(Direction.WEST);
			}
		}
	}

	/**
	 * The game drawing process.
	 * Called after each update.
	 */
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		long initTime = container.getTime();
		g.setAntiAlias(true);
		
		/*
		 * No need to render the game
		 * if it is game over/game win.
		 */
		if (stage == GameStage.GAME_WIN) {
			g.drawImage(winImage, 0, 0);
			return;
		}
		if (stage == GameStage.GAME_OVER) {
			g.drawImage(loseImage, 0, 0);
			return;
		}
		
		g.setBackground(Color.black);
		
		/*
		 * Drawing the dots.
		 */
		g.setColor(Color.white);
		for (Entity d : World.getWorld().getDots()) {
			g.fill(new Rectangle(d.getPoint().x, d.getPoint().y, DOT_SIZE, DOT_SIZE)); 
		}
		
		/*
		 * Drawing the player.
		 */
		g.drawImage(tankImage, plr.getPoint().x, plr.getPoint().y);
		
		/*
		 * Drawing the stars.
		 */
		for (Star s : World.getWorld().getStars()) {
			g.drawImage(starImage, s.getPoint().x, s.getPoint().y);
		}
		
		/*
		 * Drawing the player's bullets.
		 */
		for (Bullet b : World.getWorld().getBullets()) {
			g.setColor(b.getColor());
			g.fill(new RoundedRectangle(b.getPoint().x, b.getPoint().y, b.getWidth(), b.getHeight(), 2));
		}
		
		/*
		 * Drawing the stars' bullets.
		 */
		for (Bullet b : World.getWorld().getStarBullets()) {
			g.setColor(b.getColor());
			g.fill(new RoundedRectangle(b.getPoint().x, b.getPoint().y, b.getWidth(), b.getHeight(), 2));
		}
		
		/*
		 * Drawing debug data.
		 */
		g.setColor(Color.white);
		g.drawString("Draw Time:   " + drawTime, 5, 5);
		g.drawString("Update Time: " + updateTime, 5, 20);
		
		/*
		g.drawString("# of Bullets: " + bullets.size() + starBullets.size(), 5, 35);
		g.drawString("# of Dots   : " + dots.size(), 5, 50);
		g.drawString("# of Stars  : " + stars.size(), 5, 65);
		*/
		
		g.setColor(Color.yellow);
		g.drawString("Score: " + score, 5, Math.round(0.85 * HEIGHT));
		
		g.setColor(Color.red);
		g.drawString("Health: ", 5, Math.round(0.9 * HEIGHT));
		g.setColor(Color.green);
		for (int i = 0; i < plr.getHealth(); i++) {
			g.fillRect((i * 16) + 72, (Math.round(0.9 * HEIGHT)), 8, 16);
		}
		
		//g.fillRect(plr.getPoint().x, plr.getPoint().y, plr.getSize(), plr.getSize());
		drawTime = container.getTime() - initTime;
	}

	/**
	 * Updates variables, etc.
	 * Called before each draw.
	 */
	@Override
	public void update(GameContainer game, int arg1) throws SlickException {
		long initTime = container.getTime();
		Input input = game.getInput();
		
		if (plr.getHealth() < 1 && stage == GameStage.GAME_PLAY) {
			stage = GameStage.GAME_OVER;
		}
		if (World.getWorld().getStars().size() < 1 && stage == GameStage.GAME_PLAY) {
			stage = GameStage.GAME_WIN;
		}
		
		if (stage == GameStage.GAME_OVER || stage == GameStage.GAME_WIN) {
			return;
		}
		
		List<Entity> dotsToRemove = new ArrayList<Entity>();
		List<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		List<Star> starsToRemove = new ArrayList<Star>();
		List<Bullet> starBulletsToRemove = new ArrayList<Bullet>();
		
		/*
		 * Removes all stars that touch
		 * the bullets.
		 */
		for (Bullet b : World.getWorld().getBullets()) {
			for (Star s : World.getWorld().getStars()) {
				Rectangle starRect = new Rectangle(s.getPoint().x, s.getPoint().y, s.getWidth(), s.getHeight());
				Rectangle bulletRect = new Rectangle(b.getPoint().x, b.getPoint().y, b.getWidth(), b.getHeight());
				if (starRect.intersects(bulletRect)) {
					starsToRemove.add(s);
					bulletsToRemove.add(b);
					score += SCORE_MULTIPLIER;
				}
			}
		}
		
		for (Bullet b : World.getWorld().getStarBullets()) {
			Rectangle playerRect = new Rectangle(plr.getPoint().x, plr.getPoint().y, plr.getWidth(), plr.getHeight());
			Rectangle bulletRect = new Rectangle(b.getPoint().x, b.getPoint().y, b.getWidth(), b.getHeight());
			if (playerRect.intersects(bulletRect)) {
				starBulletsToRemove.add(b);
				plr.setHealth(plr.getHealth() - 1);
				/* score -= SCORE_MULTIPLIER; */
			}
		}
		
		/**
		 * Processing player movement
		 * if arrow keys are pressed.
		 * 
		 * TODO: Move to keyPressed??
		 */
		if (input.isKeyDown(Input.KEY_LEFT)) {
			if (plr.getPoint().x > 0)
				plr.getPoint().setLocation(plr.getPoint().x - MAX_SPEED, plr.getPoint().y);
		}
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			if (plr.getPoint().x < WIDTH - tankImage.getWidth())
				plr.getPoint().setLocation(plr.getPoint().x + MAX_SPEED, plr.getPoint().y);
		}
		/*
		if (input.isKeyDown(Input.KEY_UP)) {
			if (plr.getPoint().y > 0)
				plr.getPoint().setLocation(plr.getPoint().x, plr.getPoint().y - MAX_SPEED);
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			if (plr.getPoint().y < Game.HEIGHT)
				plr.getPoint().setLocation(plr.getPoint().x, plr.getPoint().y + MAX_SPEED);
		}
		*/
		
		if (random(DOT_OCC_RATE) == 1 && World.getWorld().getDots().size() < MAX_DOTS) {
			World.getWorld().getDots().add(new Entity(new Point(random(WIDTH), HEIGHT)));
		}
		
		/*
		 * Removing the "dead" stars.
		 */
		for (Star s : starsToRemove) {
			World.getWorld().getStars().remove(s);
		}
		
		/*
		 * Processing dot movement.
		 */
		for (Entity d : World.getWorld().getDots()) {
			d.getPoint().setLocation(d.getPoint().x, d.getPoint().y - DOT_SPEED);
		}
		/*
		 * Removing out of bounds dots.
		 */
		for (Entity d : World.getWorld().getDots()) {
			if (d.getPoint().y < 0) {
				dotsToRemove.add(d);
			}
		}
		for (Entity d : dotsToRemove) {
			World.getWorld().getDots().remove(d);
		}
		
		if (input.isKeyDown(Input.KEY_SPACE)) {
			if (canShoot) {
				World.getWorld().getBullets().add(new Bullet(new Point(plr.getPoint().x + Math.round(tankImage.getWidth() * 0.42f), plr.getPoint().y - 5), BULLET_WIDTH, BULLET_HEIGHT, getRandomColor()));
				canShoot = false;
			}
		}
		
		for (Star s : World.getWorld().getStars()) {
			if (random(STAR_SHOOT_OCC) == 0) {
				World.getWorld().getStarBullets().add(new Bullet(new Point(s.getPoint().x + Math.round(starImage.getWidth() * 0.40f), s.getPoint().y - 5), BULLET_WIDTH, BULLET_HEIGHT, getRandomColor()));
			}
		}
		
		
		/*
		 * Processing bullet movement.
		 */
		for (Bullet b : World.getWorld().getBullets()) {
			b.getPoint().setLocation(b.getPoint().x, b.getPoint().y - BULLET_SPEED);
		}
		
		/*
		 * Processing star bullet movement.
		 */
		for (Bullet b : World.getWorld().getStarBullets()) {
			b.getPoint().setLocation(b.getPoint().x, b.getPoint().y - BULLET_SPEED * -1);
		}
		
		/*
		 * Removing out of bounds bullets.
		 */
		for (Bullet b : World.getWorld().getBullets()) {
			if (b.getPoint().y < 0) {
				bulletsToRemove.add(b);
			}
		}
		for (Bullet b : bulletsToRemove) {
			World.getWorld().getBullets().remove(b);
		}
		
		/*
		 * Removing out of bounds star bullets.
		 */
		for (Bullet b : World.getWorld().getStarBullets()) {
			if (b.getPoint().y > HEIGHT) {
				starBulletsToRemove.add(b);
			}
		}
		for (Bullet b : starBulletsToRemove) {
			World.getWorld().getStarBullets().remove(b);
		}
		
		/*
		 * Processing direction movement of stars.
		 */
		for (Star s : World.getWorld().getStars()) {
			if (s.getPoint().x < 0) {
				s.setDirection(Direction.EAST);
				s.getPoint().setLocation(1, s.getPoint().y);
			}
			if (s.getPoint().x > WIDTH - starImage.getWidth()) {
				s.setDirection(Direction.WEST);
				s.getPoint().setLocation(WIDTH - starImage.getWidth() - 1, s.getPoint().y);
			}
			s.getPoint().setLocation(s.getPoint().x + s.getDirection().getDeltaX() * STAR_SPEED, s.getPoint().y);
		}
		
		updateTime = container.getTime() - initTime;
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		//stars.add(new Star(new Point(x, y), 50, 50));
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
	
	public static int random(int range) {
		return (int) (java.lang.Math.random() * (range + 1));
	}
	
	public static float random(float range) {
		return (float) (java.lang.Math.random() * (range + 1));
	}
	
	public static Color getRandomColor() {
		return new Color(random(1.0f), random(1.0f), random(1.0f), 1.0f);
	}
	
	public AppGameContainer getContainer() {
		return container;
	}
	
	public void setGameContainer(AppGameContainer container) {
		this.container = container;
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
