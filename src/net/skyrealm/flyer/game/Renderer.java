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

import net.skyrealm.flyer.model.Bullet;
import net.skyrealm.flyer.model.Entity;
import net.skyrealm.flyer.model.Star;
import net.skyrealm.flyer.model.World;
import net.skyrealm.flyer.stage.GameStage;
import static net.skyrealm.flyer.Constants.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

public class Renderer {

	/**
	 * The game drawing process. Called after each update.
	 */
	public static void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setAntiAlias(true);

		/*
		 * No need to render the game if it is game over/game win.
		 */
		if (Game.getInstance().getStage() == GameStage.GAME_WIN) {
			g.drawImage(Game.getInstance().getWinImage(), 0, 0);
			return;
		}
		if (Game.getInstance().getStage() == GameStage.GAME_OVER) {
			g.drawImage(Game.getInstance().getLoseImage(), 0, 0);
			return;
		}

		g.setBackground(Color.black);

		/*
		 * Drawing the dots.
		 */
		g.setColor(Color.white);
		for (Entity d : World.getWorld().getDots()) {
			g.fill(new Rectangle(d.getPoint().x, d.getPoint().y, DOT_SIZE,
					DOT_SIZE));
		}

		/*
		 * Drawing the player.
		 */
		g.drawImage(Game.getInstance().getTankImage(), Game.getInstance()
				.getPlayer().getPoint().x, Game.getInstance().getPlayer()
				.getPoint().y);

		/*
		 * Drawing the stars.
		 */
		for (Star s : World.getWorld().getStars()) {
			g.drawImage(Game.getInstance().getStarImage(), s.getPoint().x,
					s.getPoint().y);
		}

		/*
		 * Drawing the player's bullets.
		 */
		for (Bullet b : World.getWorld().getBullets()) {
			g.setColor(b.getColor());
			g.fill(new RoundedRectangle(b.getPoint().x, b.getPoint().y, b
					.getWidth(), b.getHeight(), 2));
		}

		/*
		 * Drawing the stars' bullets.
		 */
		for (Bullet b : World.getWorld().getStarBullets()) {
			g.setColor(b.getColor());
			g.fill(new RoundedRectangle(b.getPoint().x, b.getPoint().y, b
					.getWidth(), b.getHeight(), 2));
		}

		/*
		 * Drawing debug data.
		 */
		g.setColor(Color.white);
		g.drawString("Draw Time:   " + Game.getInstance().getDrawTime(), 5, 5);
		g.drawString("Update Time: " + Game.getInstance().getUpdateTime(), 5,
				20);

		/*
		 * g.drawString("# of Bullets: " + bullets.size() + starBullets.size(),
		 * 5, 35); g.drawString("# of Dots   : " + dots.size(), 5, 50);
		 * g.drawString("# of Stars  : " + stars.size(), 5, 65);
		 */

		g.setColor(Color.yellow);
		g.drawString("Score: " + Game.getInstance().getScore(), 5,
				Math.round(0.85 * HEIGHT));

		g.setColor(Color.red);
		g.drawString("Health: ", 5, Math.round(0.9 * HEIGHT));
		g.setColor(Color.green);
		for (int i = 0; i < Game.getInstance().getPlayer().getHealth(); i++) {
			g.fillRect((i * 16) + 72, (Math.round(0.9 * HEIGHT)), 8, 16);
		}
	}

}
