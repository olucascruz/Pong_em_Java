package main;

import java.awt.Graphics;
import java.awt.Color;

public class Paddle{
    int positionX;
    int positionY;
    int width = 10;
    int height = 100;

    public Paddle(int x, int y){
        positionX = x;
        positionY = y;
    }

    public void render(Graphics g){
        g.fillRect(positionX, positionY, width, height);
        g.setColor(new Color(255,0,0,0));
    }

    public void move(int addY)
    {
        if (positionY <0){
            positionY = 0;
        }else if(positionY > 400){
            positionY = 400;
        }else{
            positionY += addY;
        }
    }

    public void moveIA(Ball ball)
    {
        if(positionY+25 > ball.positionY+ball.sizeBall){
            move(-3);
        }else if(positionY+height/2 < ball.positionY){
            move(3);
        }
    }
}
