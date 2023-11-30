package com.yeliheng.blogcommon.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片工具类
 */
public class ImageUtils {

    /**
     * 添加文字水印
     * @param line 文字
     * @param width 图片宽度
     * @param height 图片高度
     * @param textColor 文字颜色
     * @param fontSize 文字大小
     * @return 图片输入流
     */
    public static InputStream addTextWatermark(String line, int width, int height, Color textColor, int fontSize) {
        try {
            // 创建一个带透明背景的 BufferedImage
            BufferedImage newImage = new BufferedImage(
                    width,
                    height,
                    BufferedImage.TYPE_INT_ARGB);

            // 获取 Graphics2D 对象
            Graphics2D graphics = newImage.createGraphics();
            // 设置背景透明
            newImage = graphics.getDeviceConfiguration().createCompatibleImage(110, 15, Transparency.TRANSLUCENT);
            graphics.dispose();
            graphics = newImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // 设置字体和颜色
            Font font = new Font("Arial", Font.TRUETYPE_FONT, fontSize);
            graphics.setFont(font);
            graphics.setColor(textColor);

            // 计算文本位置
            FontMetrics metrics = graphics.getFontMetrics(font);
            int x1 = (newImage.getWidth() - metrics.stringWidth(line)) / 2;
            int y = (newImage.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();

            // 添加文本
            graphics.drawString(line, x1, y);

            // 释放资源
            graphics.dispose();

            // 转换 BufferedImage 到 InputStream
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(newImage, "png", os);

            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create text image.");
        }
    }
}
