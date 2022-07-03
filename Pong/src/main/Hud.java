package main;
import java.awt.Graphics;

import java.awt.Color;
import java.awt.Font;

public class Hud {
    int playerScore = 0, enemyScore = 0;
    int screenWidth;
    int sizeFont = 64;
    public Hud(int theScreenWidth)
    {
        screenWidth = theScreenWidth;
    }

    public void updateHud(int scoreP, int scoreE)
    {
        playerScore = scoreP;
        enemyScore = scoreE;
    }
    public void render(Graphics g){
     g.setFont(new Font("Arial", Font.BOLD, sizeFont));
        g.drawString(playerScore + " x " + enemyScore, (screenWidth/2)-sizeFont, 70);
    }
    public void gameOverRender(Graphics g){
        g.fillRect(0, 0, screenWidth, 500);
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, sizeFont));
        g.drawString("Fim de jogo", (screenWidth/2)-sizeFont, 70);
    }
}
