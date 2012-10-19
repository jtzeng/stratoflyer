require 'java'

java_import 'net.skyrealm.flyer.model.Bullet'
java_import 'net.skyrealm.flyer.model.Entity'
java_import 'net.skyrealm.flyer.model.Star'
java_import 'net.skyrealm.flyer.model.World'
java_import 'net.skyrealm.flyer.stage.GameStage'
java_import 'net.skyrealm.flyer.Constants'

java_import 'org.newdawn.slick.Color'
java_import 'org.newdawn.slick.GameContainer'
java_import 'org.newdawn.slick.Graphics'
java_import 'org.newdawn.slick.geom.Rectangle'
java_import 'org.newdawn.slick.geom.RoundedRectangle'

# NOT USED IN GAME AS OF NOW - TOO SLOW.

def render(container, g)
  # setting anti-alias.
  g.set_anti_alias(true)

  # no need to render the game if the
  # current game is either won or over.
  if Game::get_instance.get_stage == GameStage::GAME_WIN
    g.draw_image(Game::get_instance.winImage, 0, 0)
    return
  elsif Game::get_instance.get_stage == GameStage::GAME_OVER
    g.draw_image(Game::get_instance.loseImage, 0, 0)
    return
  end

  g.set_background(Color::black)

  # draw the dots.
  g.set_color(Color::white)
  dot_size = Constants::DOT_SIZE
  World::get_world.get_dots.each do |d|
    g.fill(Rectangle.new(d.get_point.x, d.get_point.y, dot_size, dot_size))
  end

  # draw the player.
  g.draw_image(Game::get_instance.tankImage, \
    Game::get_instance.get_player.get_point.x, \
    Game::get_instance.get_player.get_point.y)

  # draw the stars.
  World::get_world.get_stars.each do |s|
    g.draw_image(Game::get_instance.starImage, s.get_point.x, s.get_point.y)
  end

  # draw the player's bullets.
  World::get_world.get_bullets.each do |b|
    g.set_color(b.get_color)
    g.fill(RoundedRectangle.new(b.get_point.x, b.get_point.y, \
      b.get_width, b.get_height, 2))
  end

  # draw the stars' bullets.
  World::get_world.get_star_bullets.each do |b|
    g.set_color(b.get_color)
    g.fill(RoundedRectangle.new(b.get_point.x, b.get_point.y, \
      b.get_width, b.get_height, 2))
  end

  # draw debug data.
  g.set_color(Color::white)
  g.draw_string('Draw Time:   ' + Game::get_instance.get_draw_time.to_s, 5, 5)
  g.draw_string('Update Time: ' + Game::get_instance.get_update_time.to_s, 5, 20)

  # draw the score.
  g.set_color(Color::yellow)
  g.draw_string('Score: ' + Game::getInstance.get_score.to_s, 5, \
    Math::round(0.85 * Constants::HEIGHT))

  # draw the health.
  g.set_color(Color::red)
  g.draw_string('Health: ', 5, Math::round(0.9 * Constants::HEIGHT))

  # draw the health bar.
  g.set_color(Color::green)
  (0.upto(Game::get_instance.get_player.get_health)).each do |i|
    g.fill_rect((i * 16) + 72, (Math::round(0.9 * Constants::HEIGHT)), 8, 16)
  end

end