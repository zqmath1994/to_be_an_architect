package game;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class game1 extends Frame{
	
	int x = 280 ,y = 600 ;
	ArrayList xl = new ArrayList();
	ArrayList yl = new ArrayList();
	ArrayList bulletlist = new ArrayList();
	ArrayList monster_list = new ArrayList();
	Random ra= new Random();
//	Object curDir;
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image im_bg = tk.getImage(game1.class.getClassLoader().getResource("images/background.png"));
	Image fj_bg = tk.getImage(game1.class.getClassLoader().getResource("images/hero1.png"));
	Image fj2_bg = tk.getImage(game1.class.getClassLoader().getResource("images/life.png"));
	Image im_bu = tk.getImage(game1.class.getClassLoader().getResource("images/bullet2.png"));
	Image im_bu2 = tk.getImage(game1.class.getClassLoader().getResource("images/bullet1.png"));
	Image im_monster = tk.getImage(game1.class.getClassLoader().getResource("images/enemy1.png"));
	
	private boolean left = false,right=false,up=false,down=false,J=false,K=false;
 	
//	public enum Direction{
// 		U,D,L,R,LU,LD,RU,RD,STOP
// 	}
 	
 	public void start(){
		this.setSize(600,700);
		this.setTitle("飞机大战");
//		this.setBackground(new Color(100,220,50));
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
				
			}
		});	
		
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
//					y -= 10;
					up = true;
				}else if (e.getKeyCode() == KeyEvent.VK_S) {
//					y += 10;
					down = true;
				}else if (e.getKeyCode() == KeyEvent.VK_A) {
//					x -= 10;
					right = true;
				}else if (e.getKeyCode() == KeyEvent.VK_D) {
//					x += 10;
					left = true;
				}else if (e.getKeyCode() == KeyEvent.VK_J) {
//					x += 10;
					J = true;
				}else if (e.getKeyCode() == KeyEvent.VK_K) {
//					x += 10;
					K = true;
				}
//				judgeDir();

			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
//					y -= 10;
					up = false;
				}else if (e.getKeyCode() == KeyEvent.VK_S) {
//					y += 10;
					down = false;
				}else if (e.getKeyCode() == KeyEvent.VK_A) {
//					x -= 10;
					right = false;
				}else if (e.getKeyCode() == KeyEvent.VK_D) {
//					x += 10;
					left = false;
				}else if (e.getKeyCode() == KeyEvent.VK_J) {
//					x += 10;
					J = false;
				}else if (e.getKeyCode() == KeyEvent.VK_K) {
//					x += 10;
					K = false;
				}
//				judgeDir();
			}
			
		});
		
		myThread mt = new myThread();
		mt.start();
	}
	
// 	public void judgeDir(){
// 		if (left && !right &&!up&&!down) {
// 			curDir = Direction.L;
//		}
// 		if (!left && right &&!up&&!down) {
//			curDir = Direction.R;
//		}
// 		if (!left && !right &&up&&!down) {
//			curDir = Direction.U;
//		}
// 		if (!left && !right &&!up&&down) {
//			curDir = Direction.D;
//		}
// 		if (left && !right &&up&&!down) {
//			curDir = Direction.LU;
//		}
// 		if (left && !right &&!up&&down) {
//			curDir = Direction.LD;
//		}
// 		if (!left && right &&up&&!down) {
//			curDir = Direction.RU;
//		}
// 		if (!left && !right &&!up&&down) {
//			curDir = Direction.RD;
//		}	
// 		move();
// 	}
//
// 	
// 	public void move(){
// 		
// 	}
 	int timer=0;
 	public void move() {
 		if (up==true) {
 			y -= 10;
		}
 		if (down==true) {
 			y += 10;
		}
 		if (right==true) {
 			x -= 10;
		}
 		if (left==true) {
 			x += 10;
		}
 		if (J==true && timer >=5) {
 			int speed = 15;
			Bullet b = new Bullet(x, y, im_bu,speed);
			bulletlist.add(b);
			timer = 0;
		}if (K==true) {
			monster_list.clear();
			bulletlist.clear();
		}
 	}
 	
 	int bg_y =0;
	@Override
	public void paint(Graphics g) {
		
		g.drawImage(im_bg, 0, bg_y += 5, 600, 700, null);
		g.drawImage(im_bg, 0, -700+bg_y, 600, 700, null);
		//当第一张图片完全跑出我们的窗体
		if (bg_y>=700) {
			bg_y = 0;
		}	
		g.setColor(Color.blue);
		g.drawLine(100, 0, 100, 700);
		g.drawString("边界", 80, 280);
//		g.drawLine(500, 0, 500, 700);
//		g.drawString("边界", 500, 280);
		
		g.drawImage(fj_bg, x, y, 50, 50, null);
		
		for (int i = 0; i < bulletlist.size(); i++) {
			Bullet b = (Bullet)bulletlist.get(i);	
			b.draw(g);
			if (b.live==0) {
				System.out.println("game1.paint()");
				b = null;
				bulletlist.remove(i);
			}
		}
		
		if (ra.nextInt(10) >= 9) {
			int speedx = 0;
			if (ra.nextInt(2) >= 1) {
				speedx = -(ra.nextInt(10)+1);
			}else {
				speedx = ra.nextInt(10)+1;
			}
			
			monster m = new monster( ra.nextInt(540), 0, im_monster,ra.nextInt(10)+1,speedx);
			monster_list.add(m);
		}
		
		for (int i = 0; i < monster_list.size(); i++) {
			monster m1 = (monster)monster_list.get(i);
			m1.draw(g);
			int mon_bul_x = m1.x;
			int mon_bul_y = m1.y+60;
			int speed = -8;
			
			if (ra.nextInt(100)>=90) {
				Bullet mb = new Bullet(mon_bul_x, mon_bul_y, im_bu2,speed);
				bulletlist.add(mb);	
			}
			if (m1.live == 0) {
				m1 = null;
				monster_list.remove(i);
			}
		
		}	
		
		for (int k = 0; k < bulletlist.size(); k++) {
			Bullet mb = (Bullet)bulletlist.get(k);	
			mb.draw(g);
			
			if (mb.speed > 0) {
				Rectangle rec_b = mb.getRec();//得到每一个子弹的矩形区域
				for (int m = 0; m < monster_list.size(); m++) {
					monster mo = (monster)monster_list.get(m);
					Rectangle rec_m = mo.getRec();
					
					if (rec_b.intersects(rec_m)) {
//						System.out.println("game1.paint()");
						monster_list.remove(m);
						bulletlist.remove(k);		
					}
			}
			}	
			if (mb.live == 0) {
				mb = null;
				bulletlist.remove(k);
			}
			}
			
}
	
	Image im_bf = null;
	@Override
	public void update(Graphics g) {
		//等于null 说明画布没有东西
		if (im_bf == null) {
			im_bf = this.createImage(600,700);
		}
		Graphics bg = im_bf.getGraphics();
		//paint方法里面有多少就画多少
		paint(bg);
		//这样画出来的是一张图片，不用一个个的画
		g.drawImage(im_bf, 0, 0, 600, 700, null);
	}
	
	class myThread extends Thread{
		@Override
		public void run() {
			while(true){				
			repaint();
			move();
			timer++;
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}
		
	}
	
	public static void main(String[] args) {
		game1 demo2 = new game1();
		demo2.start();
	}
}
