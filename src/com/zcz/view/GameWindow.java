package com.zcz.view;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
/**
 * GreedySname game window, which contains a GamePanel.
 * 
 * @author zhangchizhan
 * @since 2020/12/8
 */
public class GameWindow extends JFrame implements Runnable {

	private static final long serialVersionUID = -5058392146646882232L;
	
	GamePanel gp = null;
	
	public GameWindow() {
		super();
		init();
	}
	
	public void init() {
		//Initialize game panel.
		gp = new GamePanel(800, 800, 40, 40);
		gp.addKeyListener(gp);
		this.add(gp);
		this.addKeyListener(gp);
		//Initialize game window.
		this.setSize(840, 840);
		this.setLocation(500, 100);
		ImageIcon icon = new ImageIcon("img\\GreedySnake.png");
		this.setIconImage(icon.getImage());
		this.setTitle("Greedy Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Fix the window size.
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void run() {
		gp.run();
	}

}



