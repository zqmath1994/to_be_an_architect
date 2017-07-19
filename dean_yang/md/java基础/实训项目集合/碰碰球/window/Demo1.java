package window;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class Demo1 extends Frame{
	
	int x,y ;
	ArrayList al = new ArrayList();
	ArrayList cl = new ArrayList();
	

 	public void start(){
		this.setSize(700,600);
		this.setTitle("原谅球");
		this.setBackground(new Color(100,220,50));
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
				System.out.println("closed");
			}
		});
		
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				x = e.getX();
				y = e.getY();	
				al.add(x);
				al.add(y);
			repaint();
			}
			
		});		
	}
	
	//默认只执行一次
	//fillOval（xy，坐标,宽度，高度）
	Random ra= new Random();
	@Override
	public void paint(Graphics g) {
		
		g.fillOval(x, y, 30, 30);
		for(int i = 0; i<al.size();i++){
			g.setColor(new Color(ra.nextInt(255),ra.nextInt(255),ra.nextInt(255)));
			g.fillOval((int)al.get(i), (int)al.get(++i), 30, 30);
		}		
	}
	
	public static void main(String[] args) {
		Demo1 demo1 = new Demo1();
		demo1.start();
	}
}
