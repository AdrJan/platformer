package com.adrjan.platformer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animations {

    private final List<String> images = new ArrayList<>();

    private int animationCount = 0;
    private int imageIndex = 0;
    private int imageListSize;
    private final int animationMaxCnt;

    public Animations(int animMaxCnt) {
        animationMaxCnt = animMaxCnt;
    }

    public void setImages(String... imageNames) {
        for (String imageName : imageNames)
            images.add(imageName);
        imageListSize = images.size();
    }

    public BufferedImage getImage() {
        if (animationCount++ > animationMaxCnt) {
            animationCount = 0;
            if (++imageIndex >= imageListSize) imageIndex = 0;
        }
        return BufferedImageLoader.getImageByName(images.get(imageIndex));
    }
}
