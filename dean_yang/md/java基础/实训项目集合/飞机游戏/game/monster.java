package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import game.Bullet;

public class monster {
	int width=40,height=40;
	Image im;
	int x,y;
	int speedy=5;
	int speedx=5;
	int live=100;


public monster(int x,int y,Image im,int speedx,int speedy) {
	this.x = x;
	this.y = y;
	this.im = im;
	this.speedx = speedx;
	this.speedy = speedy;
}

public void  draw(Graphics g) {
	g.drawImage(im, x, y, width,height,null);
	move();
	}

Random ra = new Random();
public void move(){
	x -=speedx;
	y += speedy;
	if (x >= 560|| x<=0) {
		speedx = -speedx;
	}
	if (y>=700) {
	live = 0;	
	}
}
public Rectangle getRec(){	
	return new Rectangle(x,y,width,height);
}
}