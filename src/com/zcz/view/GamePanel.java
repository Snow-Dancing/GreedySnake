package com.zcz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.zcz.model.DirectEnum;
import com.zcz.model.GameLogic;
import com.zcz.model.Location;
import com.zcz.model.SnakeBodyPoint;
import com.zcz.utils.ThreadUtils;

/**
 * The game panel.
 * 
 * @author zhangchizhan
 * @since 2020/12/8
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = 629752198065812670L;
	
	GameLogic gameLogic;
	
	int panelWidth, panelHeight;
	int pointWidth, pointHeight;
	
	boolean gameIsRunning;
	boolean showGameOverLabel;
	
	int moveSpeed;
	boolean speedWaitToChange;
	
	public GamePanel(int panelWidth, int panelHeight, int horizontalGrids, int verticalGrids) {
		super();
		this.moveSpeed = 1;
		this.speedWaitToChange = false;
		this.gameIsRunning = false;
		this.showGameOverLabel = false;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		this.pointWidth = (int)(panelWidth / horizontalGrids);
		this.pointHeight = (int)(panelHeight / verticalGrids);
		this.gameLogic = new GameLogic(horizontalGrids, verticalGrids);
		gameLogic.init();
	}

	@Override
	public void run() {
		gameIsRunning = true;
		while (gameIsRunning) {
			// Check whether the game is over.
			if (gameLogic.checkIfGameOver()) {
				gameIsRunning = false;
				showGameOverLabel = true;
				break;
			} 
			// Expand snake body if its head can attach the next point or step the snake body.
			if (gameLogic.checkCanExpand()) {
				gameLogic.expandBody();
				gameLogic.genDefaultNextPoint();
			} else {
				gameLogic.stepSnakeBody();
			}
			// Repaint the screen. This function will automatically call function "paint()".
			repaint();
			ThreadUtils.sleep((long)(300 / moveSpeed));
		}
		// Show game over screen. The game over label will keep twinkling.
		while (true) {
			repaint();
			this.showGameOverLabel = !this.showGameOverLabel;
			ThreadUtils.sleep(500);
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		drawBackground(g);
		if (gameIsRunning) {
			drawSnake(g);
		} else {
			drawGameOver(g);
		}
	}
	
	private void drawBackground(Graphics g) {
		g.setColor(new Color(238, 232, 170));
		g.fillRect(0, 0, panelWidth, panelHeight);
	}
	
	private void drawSnake(Graphics g) {
		// Draw snake body. 
		g.setColor(Color.GREEN);
		for(SnakeBodyPoint sbp : gameLogic.getSnakeBodyPoints()) {
			drawPoint(g, sbp.getLocation());
		}
		// Draw the next point.
		g.setColor(Color.RED);
		drawPoint(g, gameLogic.getNextPoint().getLocation());
	}
	
	private void drawPoint(Graphics g, Location loc) {
		g.fill3DRect(
				loc.width * pointWidth, 
				loc.height * pointHeight, 
				pointWidth, pointHeight, 
				false
		);
	}
	
	private void drawGameOver(Graphics g) {
		if (showGameOverLabel) {
			// Show game over label.
			g.setColor(new Color(255, 20, 147));
			g.setFont(new Font("Times New Roman", Font.BOLD, 50));
			g.drawString("GAME OVER!", 250, 380);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g.drawString("Press any key to exit...", 300, 500);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (!gameIsRunning) return;
		boolean directChanges = false;
		SnakeBodyPoint head = gameLogic.getSnakeHead();
		DirectEnum headLastMoveDirect = head.getLastMoveDirection();
		if(arg0.getKeyCode() == KeyEvent.VK_UP && headLastMoveDirect != DirectEnum.DOWN) {
			// up
			head.setDirection(DirectEnum.UP);
			directChanges = true;

		}
		else if(arg0.getKeyCode() == KeyEvent.VK_DOWN && headLastMoveDirect != DirectEnum.UP) {
			// down
			head.setDirection(DirectEnum.DOWN);
			directChanges = true;		
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_LEFT && headLastMoveDirect != DirectEnum.RIGHT) {
			// left
			head.setDirection(DirectEnum.LEFT);
			directChanges = true;
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_RIGHT && headLastMoveDirect != DirectEnum.LEFT) {
			// right
			head.setDirection(DirectEnum.RIGHT);
			directChanges = true;
		}
		if (directChanges) {
			if (speedWaitToChange) {
				moveSpeed = 3;
			} else {
				speedWaitToChange = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		moveSpeed = 1;
		speedWaitToChange = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		if (!gameIsRunning) {
			System.exit(0);
		} else {
			keyPressed(arg0);
			keyReleased(arg0);
		}
	}
}
