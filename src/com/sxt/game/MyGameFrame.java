package com.sxt.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/***
 * author:rjx
 * Date:2019.09.30
 */
public class MyGameFrame extends Frame {
    Image planeImg = GameUtil.getImage("images/plane.png");
    Image bg = GameUtil.getImage("images/bg.jpg");

    Plane plane = new Plane(planeImg,250,250);
    Shell[] shells = new Shell[30];
    Explode bao;

    Date starttime = new Date();
    Date endtime;
    int period;//游戏持续的时间
    @Override
    public void paint(Graphics g) {
        Color c=g.getColor();
        g.drawImage(bg,0,0,null);
        plane.drawSelf(g);

        for(int i=0;i<shells.length;i++) {
            shells[i].drawSelf(g);

            boolean peng = shells[i].getRect().intersects(plane.getRect());
            if (peng) {
                plane.live = false;
                if (bao == null) {
                    bao = new Explode(plane.x, plane.y);
                    endtime=new Date();
                    period =(int)((endtime.getTime()-starttime.getTime())/1000);
                }
                bao.draw(g);
            }
                if(!plane.live){
                    g.setColor(Color.red);
                    Font f=new Font("宋体",Font.BOLD,50);
                    g.setFont(f);
                    g.drawString("时间:"+period+"秒",(int)plane.x,(int)plane.y);
                }
        }
        g.setColor(c);
    }

    class PaintThread extends Thread{
        @Override
        public void run() {
            while(true){
                repaint();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            plane.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            plane.minusDirection(e);
        }
    }
    public void launchFrame(){
        this.setTitle("飞机大战");
        this.setVisible(true);
        this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
        this.setLocation(300,300);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        new PaintThread().start();//启动重画窗口线程
        addKeyListener(new KeyMonitor());//增加键盘监听事件
        for(int i=0;i<shells.length;i++){
            shells[i] = new Shell();
        }
    }

    public static void main(String[] args) {
        MyGameFrame f =new MyGameFrame();
        f.launchFrame();
    }
    private Image offScreenImage = null;
    public void update(Graphics g){
        if(offScreenImage==null){
            offScreenImage =this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
        }
        Graphics gOff= offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage,0,0,null);
    }
}
