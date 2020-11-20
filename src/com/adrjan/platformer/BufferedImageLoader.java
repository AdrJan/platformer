package com.adrjan.platformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BufferedImageLoader {

    static Map<String, BufferedImage> bufferedImageMap = new HashMap<>();

    public void loadImage(String path) {
        try {
            String pathStr = getClass().getResource(path).getPath();
            bufferedImageMap.put(
                    pathStr.substring(pathStr.lastIndexOf('/')),
                    ImageIO.read(getClass().getResource(path))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getImageByName(String name) {
        return bufferedImageMap.get(name);
    }
}
