package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Bullet {
	//子弹的属性
	int width=5,height=12;
	Image im;
	int x,y;
	int speed=15;
	int demage=100;
	int live=100;
	
	public Bullet(int x,int y,Image im,int speed) {
		this.x = x+25;
		this.y = y-25;
		this.im = im;
		this.speed =speed;
	}
	
	//子弹的行为、方法，函数，功能
	public void draw(Graphics g){
		g.drawImage(im, x, y, width, height,null);
		move();
	}
	
	public void move(){
		y -= speed;
//		System.out.println(y);
//		System.out.println(speed);
		if (y<0||y>=700) {
		live = 0;	
		}
	}
	
	public Rectangle getRec(){	
		return new Rectangle(x,y,width,height);
	}
	
}
