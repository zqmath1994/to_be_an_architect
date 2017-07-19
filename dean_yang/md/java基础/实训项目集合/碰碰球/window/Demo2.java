package window;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class Demo2 extends Frame{
	
	int x,y ;
	ArrayList xl = new ArrayList();
	ArrayList yl = new ArrayList();
	Random ra= new Random();
	 
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
//				x = e.getX();
//				y = e.getY();
//				xl.add(x);
//				yl.add(y);
//			repaint();
				Oval o = new Oval(e.getX(), e.getY(), new Color(ra.nextInt(255),ra.nextInt(255),ra.nextInt(255)));
				xl.add(o);
			}
		});	
		
		myThread mt = new myThread();
		mt.start();
	}
	
	//默认只执行一次
	//fillOval（xy，坐标,宽度，高度）

//	int flag =5;
	@Override
	public void paint(Graphics g) {
		
		for (int i = 0; i < xl.size(); i++) {
//			g.setColor(new Color(ra.nextInt(255),ra.nextInt(255),ra.nextInt(255)));
//			int x = (int)xl.get(i);
//			g.fillOval(x += flag, (int)yl.get(i), 30, 30);
//			xl.set(i,x);
//			if (x >= 600) {
//				flag = -5;
//			}else if (x <= 0) {
//				flag = 5;
//			}
			Oval o = (Oval)xl.get(i);
			o.Draw(g);
			
			Rectangle rec1 = o.getRec();
			for(int j=0;j<xl.size();j++){
				Oval o1 = (Oval)xl.get(j);
				Rectangle rec2 = o1.getRec();
				
				if(rec1.intersects(rec2) && i!=j){
					o.flag = -o.flag;
					o.flag1 = -o.flag1;
					o1.flag = -o1.flag;
					o1.flag1 = -o1.flag1;
				}
			
			}
			
			
		}
	}
	
	class myThread extends Thread{

		@Override
		public void run() {
			while(true){
				
			repaint();
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}
		
	}
	
	public static void main(String[] args) {
		Demo2 demo2 = new Demo2();
		demo2.start();
	}
}
