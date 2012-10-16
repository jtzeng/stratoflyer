package net.skyrealm.flyer.util;

import org.newdawn.slick.Color;

public class Utils {
	
	public static int random(int range) {
		return (int) (java.lang.Math.random() * (range + 1));
	}
	
	public static float random(float range) {
		return (float) (java.lang.Math.random() * (range + 1));
	}
	
	public static Color getRandomColor() {
		return new Color(random(1.0f), random(1.0f), random(1.0f), 1.0f);
	}

}
