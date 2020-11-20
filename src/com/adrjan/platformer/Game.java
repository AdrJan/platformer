package com.adrjan.platformer;

import com.adrjan.platformer.framework.KeyInput;
import com.adrjan.platformer.framework.ObjectId;
import com.adrjan.platformer.objects.Block;
import com.adrjan.platformer.objects.Coin;
import com.adrjan.platformer.objects.DeathTrap;
import com.adrjan.platformer.objects.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    private boolean running = false;
    private int counter = 0;

    public static int WIDTH, HEIGHT;

    Handler handler;
    Camera camera;
    Player player;

    private BufferedImage bg = null;
    private BufferedImage blockImg = null;
    private BufferedImage playerImg = null;

    private void init() {
        WIDTH = getWidth();
        HEIGHT = getHeight();
        BufferedImageLoader loader = new BufferedImageLoader();
        loader.loadImage("/level1.png");
        loader.loadImage("/bgsimple.png");
        loader.loadImage("/block1.png");
        loader.loadImage("/block2.png");
        loader.loadImage("/block3.png");
        loader.loadImage("/block4.png");
        loader.loadImage("/robo.gif");

        handler = new Handler();
        camera = new Camera();

        player = new Player(64, 690, handler, camera, ObjectId.Player);
        this.addKeyListener(new KeyInput(handler, player));
        handler.addObject(player);
        loadImageLevel(BufferedImageLoader.getImageByName("/level1.png"));
    }

    public synchronized void start() {
        if (running)
            return;

        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        init();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void tick() {
        handler.tick();
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ObjectId.Player)
                camera.tick(handler.object.get(i));
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //DRAWING
        g.drawImage(BufferedImageLoader.getImageByName("/bgsimple.png"), 0, 0, this);
        g2d.translate(camera.getX(), camera.getY());
        handler.render(g);
        g2d.translate(-camera.getX(), -camera.getY());

        g.setColor(Color.white);

        g.setFont(new Font("Cooper Black", Font.PLAIN, 35));
        g2d.drawString("Score:    " + Player.score, 550, 40);
        g2d.drawString("Lifes: " + player.getLifes(), 30, 40);

        if (counter < 100) {
            g.setFont(new Font("Cooper Black", Font.PLAIN, counter));
            g2d.drawString("GET READY!", 250 - counter * 2, 200);
            counter++;
        }

        if (player.getLifes() == 0) {
            g.setFont(new Font("Cooper Black", Font.PLAIN, 100));
            g2d.drawString("GAME OVER", 70, HEIGHT / 2);
        }

        g.dispose();
        bs.show();
    }

    private void loadImageLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < w; xx++)
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                if (red == 255 && green == 0 && blue == 0)
                    handler.addObject(new DeathTrap(xx * 32, yy * 32, ObjectId.DeathTrap));
                if (red == 255 && green == 255 && blue == 255)
                    handler.addObject(new Block(xx * 32, yy * 32, ObjectId.Block, blockImg));
                if (red == 255 && green == 255 && blue == 0)
                    handler.addObject(new Coin(xx * 32, yy * 32, handler, ObjectId.Coin));
            }
    }

    public static void main(String[] args) {
        new Window(780, 580, "Platformer", new Game());
    }
}
