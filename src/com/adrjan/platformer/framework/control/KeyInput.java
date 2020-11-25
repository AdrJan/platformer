package com.adrjan.platformer.framework.control;

import com.adrjan.platformer.objects.player.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Player player;

    public KeyInput(Player player) {
        this.player = player;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) player.setKeyPressedA(true);
        if (key == KeyEvent.VK_D) player.setKeyPressedD(true);
        if (key == KeyEvent.VK_W) player.setKeyPressedW(true);
        if (key == KeyEvent.VK_ESCAPE) System.exit(1);
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) player.setKeyPressedA(false);
        if (key == KeyEvent.VK_D) player.setKeyPressedD(false);
        if (key == KeyEvent.VK_W) player.setKeyPressedW(false);
    }
}
