package main;
import java.awt.Graphics;
import java.awt.Color;

public class Ball {
    int positionX = 500;
    int positionY = 200;
    int speed;
    int speedX;
    int speedY;
    boolean collidePlayer, collideEnemy;
    boolean collideUpper, collideDown;
    boolean effectDown, effectUp; 
    public int playerScore = 0;
    public int enemyScore = 0;
    int sizeBall = 10;
    


    public void render(Graphics g){
        g.fillOval(positionX, positionY, sizeBall, sizeBall);
        g.setColor(new Color(255,0,0,0));

    }
    public void setSpeed(int setSpeed){
        speed = setSpeed;
        speedX = speed;
        speedY = speed/4;
    }

    public void move(){
        
        
        if(collidePlayer){
            speedX *= -1;
            speedX++;
            if(effectUp){
                speedY++;
                speed *= -1;
                effectUp = false;
            }
            else if(effectDown){
                speedY--;
                speed *= -1;
                effectDown = false;
            }
            collidePlayer = false;
        }else if(collideEnemy){
            speedX += 1;
            speedX *= -1;
            if(effectUp){
                speedY++;
                speed *= -1;
                effectUp = false;
            }
            else if(effectDown){
                speedY--;
                speed *= -1;
                effectDown = false;
            }
            collideEnemy = false;
        }else if(collideUpper){
            speedY *= -1;
            collideUpper = false;
        }else if(collideDown){
            speedY *= -1;
            collideDown = false;
        }


        positionX += speedX;
        positionY += speedY;

    }

    public void collision(int downLimit, Paddle player, Paddle enemy){
        if(positionY + speedY >downLimit-sizeBall){
            collideDown = true;
        }else if(positionY + speedY < 0){
            collideUpper = true;
        }

        if(positionX < player.positionX+player.width && (positionY > player.positionY && positionY < player.positionY + player.height)){
            collidePlayer = true;
            if(positionY > player.positionY && positionY < player.positionY + player.height/2){
                effectDown = true;
            }else if(positionY > player.positionY+player.height/2 && positionY < player.positionY + player.height){
                effectUp = true;
            }
        }
        
        if(positionX > enemy.positionX-enemy.width && (positionY > enemy.positionY && positionY < enemy.positionY + enemy.height)){
            collideEnemy = true;
            if(positionY > enemy.positionY && positionY < enemy.positionY + enemy.height/2){
                effectDown = true;
            }else if(positionY > enemy.positionY+enemy.height/2 && positionY < enemy.positionY + enemy.height){
                effectUp = true;
            }
        }
    }

    public void score(){
        if(positionX<0){
            enemyScore++;
            positionX = 500;
            speedX = speed;
        }else if(positionX>1000){
            playerScore++;
            positionX = 500;
            speedX = speed;
            speedY = speed/4;
        }
    }
}
