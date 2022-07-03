package main;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




public class Game extends Canvas implements Runnable, KeyListener
{
    public static JFrame frame;

    public int width = 1000;
    public int height = 500;
    public boolean isRunning = true;
    public Thread thread;
    public static BufferedImage image;
    public static Paddle player;
    public static Paddle enemy;
    public static Ball ball;
    public Hud hud;

    public boolean up, down;


    public Game(){
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(width, height));
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        player = new Paddle(10, 10);
        enemy = new Paddle(width-20, 10);

        ball = new Ball();
        ball.setSpeed(-4);
        hud = new Hud(width);
        initFrame();
    }

    public void initFrame(){    
        frame = new JFrame("Pong");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String arg[])
    {
        Game game = new Game();
        game.start();
    }
    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    public void tick()
    {
        if(up)
        {
            player.move(-5);
        }
        if(down)
        {
            player.move(5);
        }
        enemy.moveIA(ball);

        ball.collision(height, player, enemy);
        ball.move();
        ball.score();
        hud.updateHud(ball.playerScore, ball.enemyScore);

    }
    public void render()
    {
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        requestFocus();
        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, width, height);
        g.setColor(Color.white);
        player.render(g);
        g.setColor(Color.white);
        ball.render(g);
        g.setColor(Color.red);
        enemy.render(g);
        g.setColor(Color.WHITE);
        hud.render(g);
        if(hud.playerScore > 4 || hud.enemyScore > 4){
            g.setColor(Color.white);
            hud.gameOverRender(g);
        }
        bs.show();
    }

    @Override
    public void run()
    {
        long lastTime = System.nanoTime();
        double amountoTicks = 60.0;
        double ns = 1000000000/amountoTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();

        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta >= 1 )
            {
                tick();
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000)
            {
                System.out.println("FPS: "+ frames);
                frames = 0;
                timer += 1000;
            }
        }
        stop();
    }

    
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_W){
            up = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_S){
            down = true;
        }

    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_W){
            up = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            down = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
}
