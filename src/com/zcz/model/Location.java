package com.zcz.model;

public class Location {
	
	public int width;
	public int height;
	
	public Location() {} 
	
	public Location(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "Location [width=" + width + ", height=" + height + "]";
	}
	
	@Override
	public int hashCode() {
		return 100 * width + height;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if (!(obj instanceof Location)) {
			return false;
		}
		Location other = (Location)obj;
		return this.width == other.width && this.height == other.height;
	}

}
