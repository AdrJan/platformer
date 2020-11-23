package com.adrjan.platformer.framework.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.adrjan.platformer.framework.Handler;
import com.adrjan.platformer.objects.player.Player;

public class KeyInput extends KeyAdapter{

	Handler handler;
	Player player;
	
	public KeyInput(Handler handler, Player player){
		this.handler = handler;
		this.player = player;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_A) player.setKeyPressedA(true);
		if(key == KeyEvent.VK_D) player.setKeyPressedD(true);
		if(key == KeyEvent.VK_W) player.setKeyPressedW(true);
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_A) player.setKeyPressedA(false);
		if(key == KeyEvent.VK_D) player.setKeyPressedD(false);
		if(key == KeyEvent.VK_W) player.setKeyPressedW(false);
	}	
}
