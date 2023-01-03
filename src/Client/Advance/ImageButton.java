package Client.Advance;

import Client.SurfaceGUI.BeginGUI;

import javax.swing.*;
import java.awt.*;

/**
 * 继承JButton的类，可以将图片放入按钮中
 */
public class ImageButton extends JButton {

    public ImageButton(String imageRoot, double resizeWidth,double resizeHeight) {
        setMyIcon(imageRoot,resizeWidth,resizeHeight);

        //设置按钮透明
        setBorderPainted(false);//去边框
        setContentAreaFilled(false);//去原按钮外观
        setFocusPainted(false);//去点击时边框

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标移动至按钮变小手

    }

    /**
     * 更换按钮图片
     * @param imageRoot 图片素材路径
     * @param resizeWidth 宽度缩放比例
     * @param resizeHeight 长度缩放比例
     */
    public void setMyIcon(String imageRoot, double resizeWidth,double resizeHeight) {
        ImageIcon begin = new ImageIcon(BeginGUI.class.getResource(imageRoot));
        begin.setImage(begin.getImage().getScaledInstance((int)(begin.getIconWidth() * resizeWidth),
                (int)(begin.getIconHeight() * resizeHeight),
                Image.SCALE_DEFAULT));
        setIcon(begin);
    }
}
