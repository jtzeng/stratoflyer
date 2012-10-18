require 'java'

java_import 'java.awt.Point'

java_import 'net.skyrealm.flyer.Constants'
java_import 'net.skyrealm.flyer.game.Game'
java_import 'net.skyrealm.flyer.model.Direction'
java_import 'net.skyrealm.flyer.model.Entity'
java_import 'net.skyrealm.flyer.model.Player'
java_import 'net.skyrealm.flyer.model.Star'
java_import 'net.skyrealm.flyer.model.World'
java_import 'net.skyrealm.flyer.util.Utils'

java_import 'org.newdawn.slick.Color'
java_import 'org.newdawn.slick.Image'

# initializes the images.
def init_images
  return if Game::get_instance.tankImage != nil
  path = Constants::PATH
  Game::get_instance.tankImage = Image.new(path + Constants::TANK_IMAGE)
  Game::get_instance.starImage = Image.new(path + Constants::STAR_IMAGE).get_scaled_copy(0.025)
  Game::get_instance.winImage = Image.new(path + Constants::WIN_IMAGE)
  Game::get_instance.loseImage = Image.new(path + Constants::LOSE_IMAGE).get_scaled_copy(0.5)
end

# initializes the player.
def init_player(gw, gh)
  w = Game::get_instance.tankImage.get_width
  h = Game::get_instance.tankImage.get_height
  gw *= 0.5 and gh *= 0.8
  Player.new(Point.new(gw, gh), w, h, Color::white)
end

# clears all entities from the world.
def clear_all_entities
  World::get_world.get_dots.clear
  World::get_world.get_stars.clear
  World::get_world.get_bullets.clear
  World::get_world.get_star_bullets.clear
end

# spawns a bunch of stars.
def spawn_stars(dist_x, dist_y, height_offset, star_img)
  (dist_x.upto(Constants::WIDTH)).each do |x|
    (dist_y.upto(Constants::HEIGHT * 0.4)).each do |y|
      if x % (Utils::random(dist_y) + dist_y) == 0
        if y % (Utils::random(dist_y) + dist_y) == 0
          World::get_world.get_stars.add(Star.new( \
            Point.new(x, y), star_img.get_width, star_img.get_height))
        end
      end
    end
  end
end

# setting the default randomized directions of the stars.
def set_default_star_dir
  World::get_world.get_stars.each do |star|
    if Utils::random(1) == 1
      star.set_direction(Direction::EAST)
    else
      star.set_direction(Direction::WEST)
    end
  end
end

# spawns the initial dots on the screen.
def spawn_init_dots(amount)
  amount.times do
    World::get_world.get_dots. \
      add(Entity.new(Point.new(Utils::random( \
        Constants::WIDTH), Utils::random(Constants::HEIGHT))))
  end
end