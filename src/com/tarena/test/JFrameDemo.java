package com.tarena.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;//Frame 框架，相框
import javax.swing.JPanel;//panel 面板
import javax.swing.border.LineBorder;

/**
 * 显示窗口 与 绘图 
 */
public class JFrameDemo {
	public static void main(String[] args) {
		JFrame frame = new JFrame("窗口");
		Stage pane = new Stage();//面板
		frame.setLayout(null);//取消窗口的默认布局，取消自动充满
		frame.add(pane);//窗口添加面板
		pane.setSize(10*35, 10*35);//面板大小
		pane.setLocation(50, 50);//面板位置
		frame.setSize(450, 480);//设置窗口的大小
		pane.setBorder(new LineBorder(Color.black));//添加边框
		frame.setLocationRelativeTo(null);//frame居中
		frame.setVisible(true);
		//在Swing中如下代码可以实现对键盘事件的监听，
		//也就是获得到底哪个键盘按键被按下了
		pane.requestFocus();//使pane获取输入“焦点”，
		//也就是使pane变成键盘输入的目标
		//继承KeyAdapter比实现KeyListener 更加简洁
		//在pane上添加键盘事件的监听，获得到底哪个按键被按下
		pane.addKeyListener(new KeyAdapter(){
			//在按键按下的时候执行的方法
			public void keyPressed(KeyEvent e) {
				System.out.println("Hi"+e.getKeyCode()); 
      }
		});
		
	}
}
class Stage extends JPanel{
	
	/** 重写了 默认的绘图方法 */
	public void paint(Graphics g) {//paint 
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, this.getWidth(), getHeight());
		g.setColor(Color.RED);
		g.fill3DRect(50, 50, 30, 20, true);
	}
}



