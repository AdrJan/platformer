package com.adrjan.platformer.framework.data_loaders;

import com.adrjan.platformer.framework.Handler;
import com.adrjan.platformer.objects.ObjectId;
import com.adrjan.platformer.objects.blocks.Block;
import com.adrjan.platformer.objects.blocks.BlockUp;
import com.adrjan.platformer.objects.decor.Lamp;
import com.adrjan.platformer.objects.pick_ups.Coin;
import com.adrjan.platformer.objects.player.Player;

import java.awt.image.BufferedImage;

public class LevelLoader {

    private static Player player;

    public static void loadImageLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < w; xx++)
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                if (red == 255 && green == 255 && blue == 255)
                    Handler.addObject(new Block(xx * 64, yy * 64, ObjectId.Block));
                if (red == 255 && green == 255 && blue == 0)
                    Handler.addObject(new Coin(xx * 64, yy * 64, ObjectId.Coin));
                if (red == 0 && green == 0 && blue == 214)
                    Handler.addObject(new Lamp(xx * 64, yy * 64, ObjectId.Lamp));
                if (red == 0 && green == 139 && blue == 0)
                    Handler.addObject(new BlockUp(xx * 64, yy * 64, ObjectId.Block));
                if (red == 127 && green == 127 && blue == 127) {
                    player = new Player(xx * 64, yy * 64, ObjectId.Player);
                    Handler.addObject(player);
                }
            }
    }

    public static Player getPlayer() {
        return player;
    }
}
