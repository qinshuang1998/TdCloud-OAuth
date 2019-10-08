package com.tdxy.oauth.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * 正方验证码识别
 *
 * @author Qug_
 */
@Component
public class ImageOCR {
    private final static Logger logger = LoggerFactory.getLogger(ImageOCR.class);
    private Map<BufferedImage, String> trainMap = null;

    @Value("${zf.trainPath}")
    private String trainPath;

    private int isBlue(int colorInt) {
        Color color = new Color(colorInt);
        int rgb = color.getRed() + color.getGreen() + color.getBlue();
        if (rgb == 153) {
            return 1;
        }
        return 0;
    }

    private int isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
            return 1;
        }
        return 0;
    }

    private BufferedImage removeBackground(String picFile) throws IOException {
        BufferedImage img = ImageIO.read(new File(picFile));
        img = img.getSubimage(5, 1, img.getWidth() - 5, img.getHeight() - 2);
        img = img.getSubimage(0, 0, 50, img.getHeight());
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isBlue(img.getRGB(x, y)) == 1) {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        return img;
    }

    private List<BufferedImage> splitImage(BufferedImage img) {
        List<BufferedImage> subImgs = new ArrayList<>();
        int width = img.getWidth() / 4;
        int height = img.getHeight();
        subImgs.add(img.getSubimage(0, 0, width, height));
        subImgs.add(img.getSubimage(width, 0, width, height));
        subImgs.add(img.getSubimage(width * 2, 0, width, height));
        subImgs.add(img.getSubimage(width * 3, 0, width, height));
        return subImgs;
    }

    private Map<BufferedImage, String> loadTrainData() throws IOException {
        if (trainMap == null) {
            Map<BufferedImage, String> map = new HashMap<>(2);
            File dir = new File(trainPath);
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    map.put(ImageIO.read(file), file.getName().substring(0, 1));
                }
            }
            trainMap = map;
        }
        return trainMap;
    }

    private String getSingleCharOCR(BufferedImage img,
                                    Map<BufferedImage, String> map) {
        String result = "#";
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height;
        for (BufferedImage bi : map.keySet()) {
            int count = 0;
            if (Math.abs(bi.getWidth() - width) > 2) {
                continue;
            }
            int widthMin = Math.min(width, bi.getWidth());
            int heightMin = Math.min(height, bi.getHeight());
            Label1:
            for (int x = 0; x < widthMin; ++x) {
                for (int y = 0; y < heightMin; ++y) {
                    if (isBlack(img.getRGB(x, y)) != isBlack(bi.getRGB(x, y))) {
                        count++;
                        if (count >= min) {
                            break Label1;
                        }
                    }
                }
            }
            if (count < min) {
                min = count;
                result = map.get(bi);
            }
        }
        return result;
    }

    private void delFile(String file) throws IOException {
        Path filePath = Paths.get(file);
        Files.delete(filePath);
    }

    public String getAllOCR(String file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedImage img = removeBackground(file);
            List<BufferedImage> listImg = splitImage(img);
            Map<BufferedImage, String> map = loadTrainData();
            for (BufferedImage bi : listImg) {
                result.append(getSingleCharOCR(bi, map));
            }
            delFile(file);
        } catch (IOException e) {
            logger.error("Fail to run OCR", e);
        }
        return result.toString();
    }
}
