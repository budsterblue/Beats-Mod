package com.github.budsterblue.revolutap;


import java.io.File;

public class MenuFileItem implements Comparable<MenuFileItem>{

	private final String name, path;
	private final boolean isDir;
	private final File f;
	
	public MenuFileItem(String name, String path, boolean isDir, File f) {
		this.name = name;
		this.path = path;
		this.isDir = isDir;
		this.f = f;
	}
	
	public int compareTo(MenuFileItem another) {
		if (this.name != null) {
			return this.name.toLowerCase().compareTo(another.getName().toLowerCase());
		} else {
			throw new IllegalArgumentException();
		}
	}
	public String getName() {
		return this.name;
	}
	public String getPath() {
		return this.path;
	}
	public boolean isDirectory() {
		return this.isDir;
	}
	public File getFile() {
		return this.f;
	}

}
