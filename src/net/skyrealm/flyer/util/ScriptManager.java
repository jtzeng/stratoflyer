package net.skyrealm.flyer.util;

import java.io.BufferedReader;
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
	
	/**
	 * Private constructor to prevent
	 * instantiation (for possible confusion).
	 */
	private ScriptManager() {
	}

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
	 * @return
	 */
	public static Object executeScript(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(SCRIPT_DIRECTORY + path));
			Object o = jRuby.eval(br);
			br.close();
			return o;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ScriptException se) {
			se.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Evaluates and then executes a given
	 * method or function in a Ruby script.
	 * @param name
	 * @param method
	 * @param params
	 * @return
	 */
	public static Object executeScriptMethod(String name, String method, Object... params) {
		try {
			Invocable inv = null;
			BufferedReader br = new BufferedReader(new FileReader(SCRIPT_DIRECTORY + name));
			jRuby.eval(br);
			inv = (Invocable) jRuby;
			Object o = inv.invokeFunction(method, params);
			br.close();
			return o;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (ScriptException se) {
			se.printStackTrace();
		}
		return null;
	}

}
