package Client.Net;

import Client.Character.Character;
import Client.Character.Dir;
import Client.Protocol.Message;
import Client.SurfaceGUI.MyFrame;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 这是一个监听server发到client的Runnable类，以将对方client的角色在本client上操控
 */
public class NetThread implements Runnable {
    private MyFrame myFrame;
    private Character fighter;
    BufferedReader in;
    private Point receivedPosition;//接收到的信息中对方的位置

    public NetThread(MyFrame myFrame,Character fighter, BufferedReader in) {
        this.myFrame = myFrame;
        this.in = in;
        this.fighter = fighter;
        receivedPosition = new Point();
    }

    public Point getReceivedPosition() {
        return receivedPosition;
    }

    @Override
    public void run() {
        String fromServer = null;

        try {
            while ((fromServer = in.readLine()) != null) {
                System.out.println("client received: " + fromServer);

                //若对方未能成功触发攻击
                if(!fromServer.equals("@ATTACK@")) {
                    Message m = new Message(fromServer);//解析

                    //test
                    System.out.println(m.getKeyState()
                            +" "+ m.getKeyCode() +" "+ m.getPosition().x +" "+ m.getPosition().y);

                    receivedPosition = m.getPosition();//返回一个位置


                    //判断对方按键是按下还是松开
                    if(m.getKeyState() == Message.PRESS) {
                        System.out.println("@PRESSED");
                        //fighter.getDir().getKeyPressed(m.getKeyCode());
                        myFrame.getKeyPressed(fighter,m.getKeyCode());

                        //修正不同客户端角色的位置
                        Dir.refineMovement(myFrame.getServerFighter(),getReceivedPosition());
                    }
                    else {
                        System.out.println("@RELEASED");

                        myFrame.getKeyReleased(fighter,m.getKeyCode());

                        Dir.refineMovement(myFrame.getServerFighter(),getReceivedPosition());
                    }
                } else if(fromServer.equals("@ATTACK@")){
                    myFrame.getMyFighter().setHP(myFrame.getMyFighter().getHP() - 1);
                    myFrame.getMyFighter().getDir().fighterFall();
                }




            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
