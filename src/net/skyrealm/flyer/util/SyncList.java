package net.skyrealm.flyer.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Johan Ljungberg
 * Date: 2010-nov-27
 * Time: 11:45:33
 */

public class SyncList<T> {
	private ArrayList<T> list;
	private ArrayList<T> toRemove;
	private ArrayList<T> toAdd;
	
	public SyncList() {
		this.list = new ArrayList<T>();
		this.toRemove = new ArrayList<T>();
		this.toAdd = new ArrayList<T>();
	}
	
	public T get(int index) {
		return list.get(index);
	}
	
	public void add(T item) {
		toAdd.add(item);
	}
	
	public void remove(T item) {
		toRemove.add(item);
	}
	
	public Collection<T> getList() {
		return list;
	}
	
	public void update() {
		list.removeAll(toRemove);
		list.addAll(toAdd);
		toRemove.clear();
		toAdd.clear();
	}

}
