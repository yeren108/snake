package com.tarena.worm;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WormStage extends JPanel {
	/** 行数 */
	public static final int ROWS = 35;
	/** 列数 */
	public static final int COLS = 35;
	/** 格子大小 10个像素 */
	public static final int CELL_SIZE = 10;

	public static Image background;
	public static Image foodImage;
	public static Image cellImage;
	
	private Worm worm;
	private Cell food;

	public WormStage() {
		//如下加载图片的方法，知道即可，图片必须与WormStage.java
		//在同一个包中！
		background = Toolkit.getDefaultToolkit().
				createImage(getClass().getResource("bg.png"));
		foodImage = Toolkit.getDefaultToolkit().
				createImage(getClass().getResource("food.png"));
		cellImage = Toolkit.getDefaultToolkit().
				createImage(getClass().getResource("cell.png"));
		worm = new Worm();
		food = createFood();
	}

	/**
	 * 生成一个食物 1 生成随机数x，y 2 检查蛇是否包含x，y 2.1 如果包含 返回 1 3 创建食物节点。
	 * */
	private Cell createFood() {
		int x;
		int y;
		Random r = new Random();
		do {
			x = r.nextInt(COLS);
			y = r.nextInt(ROWS);
		} while (worm.contains(x, y));
		return new Cell(x, y);
	}

	public String toString() {
		return "worm:" + worm + "\nfood:" + food;
	}

	/** 重写绘图方法 */
	public void paint(Graphics g) {
		// 填充背景色
		g.drawImage(background, 0, 0, null);
		// 绘制食物
		g.translate(54, 49);
		g.drawImage(foodImage, 
				CELL_SIZE * food.getX(), CELL_SIZE * food.getY(), null);
		// 绘制蛇
		Cell[] cells = worm.getCells();
		for (int i = 0; i < cells.length; i++) {
			Cell node = cells[i];
			g.drawImage(cellImage, 
					CELL_SIZE * node.getX(), CELL_SIZE * node.getY(), null);

		}
	}

	/**  */

	public static void main(String[] args) {
		// 启动软件 WormStage.java
		JFrame frame = new JFrame("贪吃蛇");
		WormStage pane = new WormStage();// 面板
		frame.add(pane);// 窗口添加面板
		frame.setSize(470, 480);// 设置窗口的大小
		frame.setLocationRelativeTo(null);// frame居中
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane.action();// 启动蛇的运行
	}

	private void action() {
		// worm.creep(food);
		// repaint();//swing JPanel 中声明的方法，会尽快的启动
		// 界面的重绘功能，尽快调用paint(g) 方法绘制界面
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				// 爬行控制逻辑
				if (worm.hit()) {
					worm = new Worm();
					food = createFood();
				} else {
					boolean eat = worm.creep(food);
					if (eat) {
						food = createFood();
					}
				}
				repaint();
			}
		}, 0, 1000 / 7);
		// this 就是当前舞台面板
		this.requestFocus();
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// key 代表哪个按键被按下！
				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_UP:// 上箭头按下！
					creepTo(Worm.UP);
					break;
				case KeyEvent.VK_DOWN:// 下箭头按下！
					creepTo(Worm.DOWN);
					break;
				case KeyEvent.VK_LEFT:// 左箭头按下！
					creepTo(Worm.LEFT);
					break;
				case KeyEvent.VK_RIGHT:// 右箭头按下！
					creepTo(Worm.RIGHT);
					break;
				}
			}
		});// addKeyListener
	}// action()

	/** 爬行控制方法，在按键按下时候调用 */
	private void creepTo(int direction) {
		if (worm.hit(direction)) {
			worm = new Worm();
			food = createFood();
		} else {
			boolean eat = worm.creep(direction, food);
			if (eat) {
				food = createFood();
			}
		}
		repaint();
	}
}// WormStage

