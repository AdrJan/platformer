package com.adrjan.platformer.framework;

import com.adrjan.platformer.framework.control.KeyInput;
import com.adrjan.platformer.framework.data_loaders.BufferedImageLoader;
import com.adrjan.platformer.framework.data_loaders.LevelLoader;
import com.adrjan.platformer.framework.visual.Camera;
import com.adrjan.platformer.objects.player.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static int WIDTH, HEIGHT;
    private boolean running = false;

    private void init() {
        WIDTH = getWidth();
        HEIGHT = getHeight();

        LevelLoader.loadImageLevel(BufferedImageLoader.getImageByName("level1.png"));
        this.addKeyListener(new KeyInput(LevelLoader.getPlayer()));
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
        Handler.tick();
        for (int i = 0; i < Handler.gameObjects.size(); i++)
            if (Handler.gameObjects.get(i) instanceof Player)
                Camera.tick(Handler.gameObjects.get(i));
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
        g.drawImage(BufferedImageLoader.getImageByName("bg_1.png"), 0, 0, this);
        g.drawImage(BufferedImageLoader.getImageByName("bg_2.png"), 0, 100, this);
        g2d.translate(Camera.getX(), Camera.getY());
        Handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Window(780, 580, "Platformer", new Game());
    }
}
