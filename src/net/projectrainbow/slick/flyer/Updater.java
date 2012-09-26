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

import net.projectrainbow.slick.flyer.model.Bullet;
import net.projectrainbow.slick.flyer.model.Direction;
import net.projectrainbow.slick.flyer.model.Entity;
import net.projectrainbow.slick.flyer.model.Star;
import net.projectrainbow.slick.flyer.model.World;
import net.projectrainbow.slick.flyer.stage.GameStage;
import net.projectrainbow.slick.flyer.util.Utils;
import static net.projectrainbow.slick.flyer.Constants.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Updater {

	/**
	 * Updates variables, etc. Called before each draw.
	 */
	public static void update(GameContainer game, int arg1)
			throws SlickException {
		Input input = game.getInput();

		/*
		 * Game over/win checks.
		 */
		if (Game.getInstance().getPlayer().getHealth() < 1
				&& Game.getInstance().getStage() == GameStage.GAME_PLAY) {
			Game.getInstance().setStage(GameStage.GAME_OVER);
		}
		if (World.getWorld().getStars().size() < 1
				&& Game.getInstance().getStage() == GameStage.GAME_PLAY) {
			Game.getInstance().setStage(GameStage.GAME_WIN);
		}

		if (Game.getInstance().getStage() == GameStage.GAME_OVER
				|| Game.getInstance().getStage() == GameStage.GAME_WIN) {
			return;
		}

		List<Entity> dotsToRemove = new ArrayList<Entity>();
		List<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		List<Star> starsToRemove = new ArrayList<Star>();
		List<Bullet> starBulletsToRemove = new ArrayList<Bullet>();

		/*
		 * Removes all stars that touch the bullets.
		 */
		for (Bullet b : World.getWorld().getBullets()) {
			for (Star s : World.getWorld().getStars()) {
				Rectangle starRect = new Rectangle(s.getPoint().x,
						s.getPoint().y, s.getWidth(), s.getHeight());
				Rectangle bulletRect = new Rectangle(b.getPoint().x,
						b.getPoint().y, b.getWidth(), b.getHeight());
				if (starRect.intersects(bulletRect)) {
					starsToRemove.add(s);
					bulletsToRemove.add(b);
					Game.getInstance().setScore(
							Game.getInstance().getScore() + SCORE_MULTIPLIER);
				}
			}
		}

		/*
		 * Reduce the player's health if he or she
		 * touches the boundaries of the bullets.
		 */
		for (Bullet b : World.getWorld().getStarBullets()) {
			Rectangle playerRect = new Rectangle(Game.getInstance().getPlayer()
					.getPoint().x, Game.getInstance().getPlayer().getPoint().y,
					Game.getInstance().getPlayer().getWidth(), Game
							.getInstance().getPlayer().getHeight());
			Rectangle bulletRect = new Rectangle(b.getPoint().x,
					b.getPoint().y, b.getWidth(), b.getHeight());
			if (playerRect.intersects(bulletRect)) {
				starBulletsToRemove.add(b);
				Game.getInstance()
						.getPlayer()
						.setHealth(
								Game.getInstance().getPlayer().getHealth() - 1);
				/* score -= SCORE_MULTIPLIER; */
			}
		}

		/**
		 * Processing player movement if arrow keys are pressed.
		 * 
		 * TODO: Move to keyPressed??
		 */
		if (input.isKeyDown(Input.KEY_LEFT)) {
			if (Game.getInstance().getPlayer().getPoint().x > 0)
				Game.getInstance()
						.getPlayer()
						.getPoint()
						.setLocation(
								Game.getInstance().getPlayer().getPoint().x
										- MAX_SPEED,
								Game.getInstance().getPlayer().getPoint().y);
		}
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			if (Game.getInstance().getPlayer().getPoint().x < WIDTH
					- Game.getInstance().getTankImage().getWidth())
				Game.getInstance()
						.getPlayer()
						.getPoint()
						.setLocation(
								Game.getInstance().getPlayer().getPoint().x
										+ MAX_SPEED,
								Game.getInstance().getPlayer().getPoint().y);
		}

		/*
		 * Adds new dots, if any.
		 */
		if (Utils.random(DOT_OCC_RATE) == 1
				&& World.getWorld().getDots().size() < MAX_DOTS) {
			World.getWorld().getDots()
					.add(new Entity(new Point(Utils.random(WIDTH), 0)));
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
			d.getPoint()
					.setLocation(d.getPoint().x, d.getPoint().y + DOT_SPEED);
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

		/*
		 * Handles shooting and bullet instantiation.
		 */
		if (input.isKeyDown(Input.KEY_SPACE)) {
			if (Game.getInstance().canShoot()) {
				World.getWorld()
						.getBullets()
						.add(new Bullet(new Point(Game.getInstance()
								.getPlayer().getPoint().x
								+ Math.round(Game.getInstance().getTankImage()
										.getWidth() * 0.42f), Game
								.getInstance().getPlayer().getPoint().y - 5),
								BULLET_WIDTH, BULLET_HEIGHT, Utils
										.getRandomColor()));
				Game.getInstance().setCanShoot(false);
			}
		}

		/*
		 * Handles the bullet shooting of the stars.
		 */
		for (Star s : World.getWorld().getStars()) {
			if (Utils.random(STAR_SHOOT_OCC) == 0) {
				World.getWorld()
						.getStarBullets()
						.add(new Bullet(new Point(s.getPoint().x
								+ Math.round(Game.getInstance().getStarImage()
										.getWidth() * 0.40f),
								s.getPoint().y - 5), BULLET_WIDTH,
								BULLET_HEIGHT, Utils.getRandomColor()));
			}
		}

		/*
		 * Processing bullet movement.
		 */
		for (Bullet b : World.getWorld().getBullets()) {
			b.getPoint().setLocation(b.getPoint().x,
					b.getPoint().y - BULLET_SPEED);
		}

		/*
		 * Processing star bullet movement.
		 */
		for (Bullet b : World.getWorld().getStarBullets()) {
			b.getPoint().setLocation(b.getPoint().x,
					b.getPoint().y - BULLET_SPEED * -1);
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
			if (s.getPoint().x > WIDTH
					- Game.getInstance().getStarImage().getWidth()) {
				s.setDirection(Direction.WEST);
				s.getPoint().setLocation(
						WIDTH - Game.getInstance().getStarImage().getWidth()
								- 1, s.getPoint().y);
			}
			s.getPoint().setLocation(
					s.getPoint().x + s.getDirection().getDeltaX() * STAR_SPEED,
					s.getPoint().y);
		}
	}

}
