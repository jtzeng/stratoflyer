require 'java'

java_import 'java.awt.Point'
java_import 'net.skyrealm.flyer.Constants'
java_import 'net.skyrealm.flyer.game.Game'
java_import 'net.skyrealm.flyer.model.Direction'
java_import 'net.skyrealm.flyer.model.Entity'
java_import 'net.skyrealm.flyer.model.Star'
java_import 'net.skyrealm.flyer.model.World'
java_import 'net.skyrealm.flyer.util.Utils'

# spawns a bunch of stars.
def spawn_stars(dist_x, dist_y, height_offset, star_img)
  (dist_x.upto(Constants::WIDTH)).each do |x|
    (dist_y.upto(Constants::HEIGHT * 0.4)).each do |y|
      if x % (Utils::random(dist_y) + dist_y) == 0 and y % (Utils::random(dist_y) + dist_y) == 0
        World::get_world.get_stars.add(Star.new( \
          Point.new(x, y), star_img.get_width, star_img.get_height))
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