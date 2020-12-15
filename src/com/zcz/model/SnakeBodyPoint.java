package com.zcz.model;

/**
 * The point of the snake body.
 * 
 * @author zhangchizhan
 * @since 2020/12/8
 *
 */
public class SnakeBodyPoint {
	
	private Location loc;
	private DirectEnum curDirect, lastMoveDirect;
	
	public SnakeBodyPoint(int width, int height, DirectEnum direct) {
		this(new Location(width, height), direct);
	}
	
	public SnakeBodyPoint(Location loc, DirectEnum direct) {
		this.curDirect = direct;
		this.lastMoveDirect = direct;
		this.loc = loc;
	}
	
	public Location getNextLocation() {
		int nextWidth = loc.width, nextHeight = loc.height;
		switch(curDirect) {
		case UP:
			nextHeight -= 1;
			break;
		case DOWN:
			nextHeight += 1;
			break;
		case LEFT:
			nextWidth -= 1;
			break;
		case RIGHT:
			nextWidth += 1;
			break;
		default:
			break;
		}
		return new Location(nextWidth, nextHeight);
	}
	
	public void setLocation(Location loc) {
		setLocation(loc.width, loc.height);
		lastMoveDirect = curDirect;
	}
	
	public void setLocation(int width, int height) {
		this.loc = new Location(width, height);
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public void setDirection(DirectEnum newDirect) {
		this.curDirect = newDirect;
	}
	
	public DirectEnum getDirection() {
		return curDirect;
	}
	
	public DirectEnum getLastMoveDirection() {
		return lastMoveDirect;
	}
	
}