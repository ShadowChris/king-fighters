package Client.Character;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//动作类，实现人物行走等基本功能
public class Dir {
    private Image currentMovement;//设置当前动作
    private boolean currentDir;//保存目标当前方向
    private Map<String,Image> moveMap = new HashMap<String, Image>();//动作对应地图

    public boolean LF;//往右前进
    public boolean LS;//往右站立
    public boolean LU;//右上
    public boolean LD;//右下

    public boolean RF;//往左前进
    public boolean RS;//朝左站立
    public boolean RU;//左上
    public boolean RD;//左下

    public boolean A;//攻击
    public boolean FALL;//被击倒



    public Dir(boolean isLeft) {//初始状态是面向左还是面向右
        LF = false;
        LU = false;
        LD = false;

        RF = false;
        RU = false;
        RD = false;

        LS = isLeft;
        RS = !isLeft;

        //攻击和击倒
        A = false;
        FALL = false;
    }



    public void createMap(ArrayList<Image> movements) {
        //将movement做成maps
        //Left Forward, Left Stand, ...往右走，往右停，往左走，往左停,左攻击，右攻击，左倒，右倒
        String[] keys = {"LF","LS","RF","RS","LA","RA","LH","RH"};
        for(int i = 0; i < movements.size(); i++) {
            moveMap.put(keys[i],movements.get(i));
        }
        if(getCurrentDir()) setCurrentMovement(moveMap.get("LS"));
        else setCurrentMovement(moveMap.get("RS"));
    }//创建动作图对应maps

    public void locateDirection() {
        if(!LF && !RF && !RU && !LU && !RD && //无动作触发，站立
                !LD && !A && !FALL) {//如果没有其他动作，就站立
            if (getCurrentDir() == Character.LEFT) {
                LS = true;
                setCurrentMovement(getMoveMap().get("LS"));
            } else {
                RS = true;
                setCurrentMovement(getMoveMap().get("RS"));
            }
        } else if(FALL) {//如果检测被击倒
            if(getCurrentDir() == Character.LEFT) setCurrentMovement(getMoveMap().get("LH"));//如果LA为真，则触发攻击动作
            else  setCurrentMovement(getMoveMap().get("RH"));

        }
        else if(A) {//若检测到攻击键按下
            if(getCurrentDir() == Character.LEFT) setCurrentMovement(getMoveMap().get("LA"));//如果LA为真，则触发攻击动作
            else  setCurrentMovement(getMoveMap().get("RA"));
        } else {
            if (LF || LU || LD) {
                setCurrentMovement(getMoveMap().get("LF"));//否则前进
            } else if (RF || RU || RD){
                setCurrentMovement(getMoveMap().get("RF"));
            }
        }
    }//确定方向

    public int limitLocation(int position, int speed, int min, int max,boolean isPlus) {
        int tmp = 0;
        if(isPlus) {
            tmp = position + speed;
        } else tmp = position - speed;
        if(tmp >= min && tmp < max) return tmp;
        else return position;
    }//限制角色走出范围

    public void move(Graphics g, Character character) {//更新图片
        if(!LS && !RS && !FALL) {//移动
            if (LF) {//往右走
                character.getPosition().x = limitLocation(character.getPosition().x,character.getSPEED(),0,780,true);
            }
            if (RF) {//往左走
                character.getPosition().x = limitLocation(character.getPosition().x,character.getSPEED(),0,780,false);
            }
            if (RU || LU) {//往上走
                character.getPosition().y = limitLocation(character.getPosition().y,character.getSPEED(),150,390,false);
            }
            if (RD || LD) {//往下走
                character.getPosition().y = limitLocation(character.getPosition().y,character.getSPEED(),150,390,true);
            }
        }

        character.drawCurrentMovement(g);
    }//实现上下左右行走

    //修正服务器控制角色的位置，缓解两个客户端同一个角色出现的位置错位情况
    public static void refineMovement(Character character, Point position) {
        character.getPosition().setLocation(position);
    }

    //触发角色被击倒画面
    public void fighterFall() {
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                FALL = true;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                FALL = false;
            }
        };
        Thread thread1 = new Thread(task1);
        thread1.start();
    }

    public Image getCurrentMovement() {
        return currentMovement;
    }
    public void setCurrentDir(boolean currentDir) {
        this.currentDir = currentDir;
    }
    public boolean getCurrentDir() {
        return currentDir;
    }
    public Map<String, Image> getMoveMap() {
        return moveMap;
    }
    public void setCurrentMovement(Image currentMovement) {
        this.currentMovement = currentMovement;
    }
}
