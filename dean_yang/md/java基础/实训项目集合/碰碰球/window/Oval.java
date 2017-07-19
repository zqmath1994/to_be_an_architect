package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Oval {

	//属性
	int width=30,height=30;
	Color c;
	int x,y;
	int flag=5,flag1=5;
	
	//行为，方法，功能
	public Oval(int x,int y,Color c){
		this.x = x;
		this.y = y;
		this.c = c;
		
	}
	
	public void move() {
		
		x += flag;
		y += flag1;
		
		if (x >= 700) {
			flag = -7;
		}else if (x <= 0) {
			flag = 7;
		}
			
		if (y >= 600) {
			flag1 = -7;
		}else if (y <= 0) {
			flag1 = 7;
		}
	}

	public void Draw(Graphics g){
		g.setColor(c);
		g.fillOval(x,y,width,height);
		move();
	}
	
	//retangle 矩形区域
	public Rectangle getRec(){
		return new Rectangle(x,y,width,height);
	}
}
