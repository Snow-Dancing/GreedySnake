package com.zcz.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.zcz.utils.RandomUtils;
/**
 * The logic of Greedy Snake Game.
 * 
 * The board is a grid panel, with M X N grids.
 * 
 * Take computer screen as an example, "boardWidth" denotes the grid number in horizontal direction,
 * the index increases from left to right; "boardHeight" denotes the grid number in vertical direction,
 * the index increases from up to down. This information is very important to locate a point in screen.
 * 
 * @author zhangchizhan
 * @since 2020/12/8
 */
public class GameLogic {
	
	private int horizontalGrids, verticalGrids;
	
	private SnakeBodyPoint nextPoint;

	private LinkedList<SnakeBodyPoint> snakeBody;
	
	public GameLogic(int boardWidth, int boardHeight) {
		this.horizontalGrids = boardWidth;
		this.verticalGrids = boardHeight;
		this.snakeBody = new LinkedList<SnakeBodyPoint>();
	}
	
	public void init() {
		randomGenNextPoint(2, horizontalGrids - 2, 2, verticalGrids - 2);
		expandBody();
		genDefaultNextPoint();
	}
	
	public void genDefaultNextPoint() {
		randomGenNextPoint(0, horizontalGrids, 0, verticalGrids);
	}
	
	private void randomGenNextPoint(int minWid, int maxWid, int minHei, int maxHei) {
		if (snakeBody.size() == horizontalGrids * verticalGrids) {
			return;
		}
		List<Location> optLocations = new ArrayList<Location>();
		for (int w = minWid; w < maxWid; w++) {
			for (int h = minHei; h < maxHei; h++) {
				Location loc = new Location(w, h);
				if (!hitBodyByLocation(loc)) {
					optLocations.add(loc);
				}
			}
		}
		int randIndex = RandomUtils.getRandom().nextInt(optLocations.size());
		nextPoint = new SnakeBodyPoint(optLocations.get(randIndex), DirectEnum.STAY);
	}
	
	public void expandBody() {
		DirectEnum nextDirect = snakeBody.size() == 0 ? DirectEnum.DOWN : getSnakeHead().getDirection();
		nextPoint.setDirection(nextDirect);
		snakeBody.addFirst(nextPoint);
	}
	
	private boolean overlapTwoLocations(Location loc1, Location loc2) {
		return loc1.equals(loc2);
	}
	
	private boolean hitBodyByLocation(Location otherLoc) {
		for(SnakeBodyPoint sbp : snakeBody) {
			if(overlapTwoLocations(sbp.getLocation(), otherLoc)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkCanExpand() {
		if (snakeBody.size() == 0) return true;
		return snakeBody.getFirst().getNextLocation().equals(nextPoint.getLocation());
	}
	
	public boolean checkIfGameOver() {
		Location headNextLocation = snakeBody.getFirst().getNextLocation();
		int w = headNextLocation.width, h = headNextLocation.height;
		if(w < 0 || w >= horizontalGrids|| h < 0 || h >= verticalGrids) {
			return true;
		}
		return hitBodyByLocation(headNextLocation);
	}
	
	public void stepSnakeBody() {
		DirectEnum preDirect = getSnakeHead().getDirection();
		Location prePos = getSnakeHead().getNextLocation();
		for (SnakeBodyPoint sbp : snakeBody) {
			DirectEnum curDirect = sbp.getDirection();
			Location curPos = sbp.getLocation();
			sbp.setDirection(preDirect);
			sbp.setLocation(prePos);
			preDirect = curDirect;
			prePos = curPos;
		}
	}
	
	public List<SnakeBodyPoint> getSnakeBodyPoints() {
		return snakeBody;
	}
	
	public SnakeBodyPoint getSnakeHead() {
		return snakeBody.getFirst();
	}
	
	public SnakeBodyPoint getNextPoint() {
		return nextPoint;
	}
}
