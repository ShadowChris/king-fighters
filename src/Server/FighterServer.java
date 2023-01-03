package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class FighterServer {
    private ServerSocket serverSocket;
    private Vector<ServerThread> threads;
    private final int CLIENT_NUM = 2;
    /**
     * 装线程，便于管理
     */
    public FighterServer() {
        try {
            //服务端地址
            InetAddress ip4 = Inet4Address.getLocalHost();
            System.out.println("server IP address: " + ip4.getHostAddress());

            System.out.println("Server: Waiting for connection...");
            serverSocket = new ServerSocket(9999);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Didn't listen the port 9999");
            System.exit(1);
        }
        threads = new Vector<ServerThread>();
    }

    /**
     * 将某条消息发布到所有客户端线程中
     * @param s
     */
    public void publish(String s) {
        for(ServerThread tmpThreads: threads) {
            tmpThreads.out.println(s);
            tmpThreads.out.flush();
        }
    }

    /**
     * 两个线程分别与两个客户端建立联系，
     * 读取某一方客户端发来的指令，
     * 输出本线程客户端发送的指令到对方线程的客户端
     */
    class ServerThread extends Thread {//服务器线程
        Socket socket = null;
        private PrintWriter out = null;
        private BufferedReader in = null;
        private int playerNumber;//玩家1在左，玩家2在右

        public ServerThread(Socket s,int playerNumber) {
            this.socket = s;
            this.playerNumber = playerNumber;//线程编号

            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.out.println("PLAYER_" + playerNumber);//告诉客户端玩家号

            } catch (IOException e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        }

        //server起到转接的作用，将一个client的位置信息以server为中转发送给另一个client
        @Override
        public void run() {
            String fromUser = null;
            try {
                while ((fromUser = in.readLine()) != null) {

                    System.out.println("Receive: " + fromUser);//收到
                    //TO DO
                    if (fromUser.equals("@EXIT@")) {
                        break;//接收到退出信息
                    }

                    if (playerNumber == 1) {
                        threads.get(1).out.println(fromUser);
                    } else if (playerNumber == 2) {
                        threads.get(0).out.println(fromUser);
                    }


                    //publish(fromUser);//将指令发送到每一个线程
                }

            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    in.close();
//                    serverSocket.close();
                    socket.close();
                    threads.remove(threads.indexOf(currentThread()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println("out of connection!");
                System.out.println("Threads: " + threads.size());
            }

        }

        //得到当前线程的socket流
        public PrintWriter getOut() {
            return out;
        }
        public BufferedReader getIn() {
            return in;
        }
    }


    public void launchServer() {

        while(true) {
            //只允许两个线程进入
            if(threads.size() < CLIENT_NUM) {
                for (int i = 1; i <= CLIENT_NUM; i++) {
                    try {
                        ServerThread st = new ServerThread(serverSocket.accept(), i);

                        threads.add(st);

                        //test
                        System.out.println("Threads: " + threads.size());
                        st.start();

                        System.out.println("Server: Connected.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //创建完两个角色后，告诉客户端可以进入游戏
                publish("ACTION");
            }
        }
    }


    public static void main(String[] args) throws IOException {

        FighterServer server = new FighterServer();
        server.launchServer();

    }
}