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

package net.skyrealm.flyer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.script.Invocable;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.ScriptContext;

/**
 * ScriptManager.java
 * @author Mew
 *
 * Calls and manages Ruby scripts.
 *
 */

public class ScriptManager {
	
	/**
	 * The path to the folder
	 * where scripts can be found.
	 */
	public static final String SCRIPT_DIRECTORY = "./scripts/";

	private static ScriptEngineManager manager;
	private static ScriptEngine jRuby;
	private static ScriptContext context;
	
	private static BufferedReader br;
	
	/**
	 * Initializes the ScriptManager.
	 */
	public static void initialize() {
		manager = new ScriptEngineManager();
		jRuby = manager.getEngineByName("jruby");
		context = jRuby.getContext();
	}

	/**
	 * Evaluates a Ruby script.
	 * @param path
	 */
	public static void executeScript(String path) {
		try {
			br = new BufferedReader(new FileReader(SCRIPT_DIRECTORY + path));
			jRuby.eval(br);
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ScriptException se) {
			se.printStackTrace();
		}
	}
	
	/**
	 * Evaluates all scripts.
	 */
	public static void evaluateAllScripts() {
		File scriptDir = new File(SCRIPT_DIRECTORY);
		for (String s : scriptDir.list()) {
			executeScript(s);
		}
	}
	
	/**
	 * Executes a given function
	 * in a Ruby script.
	 * @param name
	 * @param method
	 * @param params
	 * @return
	 */
	public static Object executeFunction(String name, String method, Object... params) {
		try {
			Invocable inv = null;
			inv = (Invocable) jRuby;
			return inv.invokeFunction(method, params);
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (ScriptException se) {
			se.printStackTrace();
		}
		return null;
	}

}
