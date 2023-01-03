package Client.SurfaceGUI;

import Client.Advance.ImageButton;
import Client.Character.Character;
import Client.Net.NetClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintStream;

/**
 * 成功连接上网络，开启等待的界面
 */
public class SuccessGUI extends JFrame {
    private NetClient nc;
    PrintStream out;
    BufferedReader in;
    public SuccessGUI(NetClient nc, BufferedReader in, PrintStream out) {
        super("The King of Fighters");
        this.nc = nc;
        this.in = in;
        this.out = out;

        setSize(955 / 5 * 2,700 / 5 * 2);
        launchFrame();


        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                nc.launchGUI(SuccessGUI.this,in,out);
            }
        }).start();
    }

    class Instruction extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Toolkit tk =Toolkit.getDefaultToolkit();
            Image poster = tk.getImage(Character.class.getClassLoader().getResource("images/instruction/success.png"));//加载背景
            g.drawImage(poster,0,0,poster.getWidth(null) / 5 * 2,
                    poster.getHeight(null) / 5 * 2,null);

            Image loading = tk.getImage(Character.class.getClassLoader().getResource("images/component/loading.gif"));//加载背景
            g.drawImage(loading,80,170,loading.getWidth(null),loading.getHeight(null),null);
            repaint();
        }
    }

    public void launchFrame() {
        SuccessGUI.Instruction instruction = new SuccessGUI.Instruction();
        this.add(instruction);

        instruction.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        ImageButton beginButton = new ImageButton("/images/instruction/back.png",2.0 / 5, 2.0 / 5);

        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(beginButton);
        instruction.add(panel, BorderLayout.SOUTH);
    }

//    public static void main(String[] args) {
//        SuccessGUI s = new SuccessGUI(null,null,null);
//    }
}
