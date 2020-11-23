package com.adrjan.platformer.framework.data_loaders;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BufferedImageLoader {

    private static final Map<String, BufferedImage> bufferedImageMap;

    static {
        bufferedImageMap = new HashMap<>();
        File[] imageFiles = getImagesFromResource();
        try {
            for (File file : imageFiles)
                bufferedImageMap.put(file.getName(), ImageIO.read(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File[] getImagesFromResource() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("images");
        String path = url.getPath();
        return new File(path).listFiles();
    }

    public static BufferedImage getImageByName(String name) {
        return bufferedImageMap.get(name);
    }

    public static BufferedImage rotateImageHorizontally(BufferedImage bufferedImage) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-bufferedImage.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(bufferedImage, null);
    }
}
