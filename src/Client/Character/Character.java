package Client.Character;

import java.awt.*;

import java.util.ArrayList;


public class Character {
    public static final boolean LEFT = true;
    public static final boolean RIGHT = false;

    private String name;//角色名字

    private int HP = 5;//血量

    private  ArrayList<Image> movements;//人物角色的动作：左右停

    private int SPEED = 10;//当前移动速度

    private Point position;//当前位置

    private Dir dir;//动作按钮类


    public Character(String name,boolean leftOrRight,String rootDir,int x,int y) {
        Toolkit tk =Toolkit.getDefaultToolkit();
        this.name = name;

        movements = new ArrayList<Image>();
        position = new Point(x,y);


        for(int i = 1; i <= 8; i++) {//将图片加载到ArrayList
            movements.add(tk.getImage(Character.class.getClassLoader()
                    .getResource(rootDir + "/" + i + ".gif")));
        }

        dir = new Dir(leftOrRight);//监控动作
        dir.setCurrentDir(leftOrRight);
        dir.createMap(movements);//创建对应动作maps

    }//将图片载入缓存区, 并且做好索引


    //将当前动作画上面板（可以重写调整每一帧动作的图片位置）
    protected void drawCurrentMovement(Graphics g) {
        Image currentMovement = dir.getCurrentMovement();
        dir.locateDirection();//更新目前的动作
        g.drawImage(currentMovement,
                (int)position.getX(),
                (int)position.getY(),
                currentMovement.getWidth(null),
                currentMovement.getHeight(null),null);

    }

    /**
     * 当fighter1成功攻击fighter2时，返回true，否则返回false
     * @param fighter1
     * @param fighter2
     * @return
     */
    public static boolean isAttacked(Character fighter1,Character fighter2) {
        Point p1 = fighter1.getPosition();
        Point p2 = fighter2.getPosition();

        //攻击有效范围
        if(!fighter2.getDir().FALL &&
                ((p1.x <= p2.x && p1.x + 60 >= p2.x) || (p2.x <= p1.x && p2.x + 50 >= p1.x)) &&
                ((p1.y <= p2.y && p1.y + 10 >= p2.y) || (p2.y <= p1.y && p2.y + 10 >= p1.y))) {
            System.out.println("attack!");
            return true;
        } else return false;

    }

    public Dir getDir() {
        return dir;
    }
    public Point getPosition() {
        return position;
    }
    public int getSPEED() {
        return SPEED;
    }
    public int getHP() {
        return HP;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }
}

