package Client.Protocol;

import java.awt.*;
import java.util.StringTokenizer;

/**
 * 字符串包装解析类，解析包装信息
 */
public class Message {
    public final static boolean PRESS = true;
    public final static boolean RELEASE = false;

    private boolean keyState;//按键按下或松开
    private Point position;//对方客户端角色位置
    private int keyCode;//按下的按键

    public Message(String message) {
        StringTokenizer st = new StringTokenizer(message,"_");

        String tmp = st.nextToken();
        if(tmp.equals("true")) keyState = PRESS;
        else if(tmp.equals("false"))keyState = RELEASE;

        keyCode = Integer.valueOf(st.nextToken());

        int x = Integer.valueOf(st.nextToken());
        int y = Integer.valueOf(st.nextToken());

        position = new Point(x,y);
    }

    public Message(boolean keyState,int keyCode,int x,int y) {
        this.keyState = keyState;
        this.keyCode = keyCode;
        this.position = new Point(x,y);
    }

    public Point getPosition() {
        return position;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public boolean getKeyState() {
        return keyState;
    }

    public String toString() {
        return keyState + "_" + keyCode + "_" + position.x + "_" + position.y;
    }
}
