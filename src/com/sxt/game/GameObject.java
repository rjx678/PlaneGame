package com.sxt.game;

import java.awt.*;

public class GameObject {
    int speed;
    Image img;
    double x,y;
    int width,height;

    public GameObject(int speed, Image img, double x, double y, int width, int height) {
        this.speed = speed;
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GameObject(Image img, double x, double y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }

    public void drawSelf(Graphics g){
        g.drawImage(img,(int)x,(int)y,null);
    }
    public GameObject(){
    }
    public Rectangle getRect(){
        return  new Rectangle((int)x,(int)y,width,height);
    }
}
