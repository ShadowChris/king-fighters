package Client.SurfaceGUI;

import Client.Advance.ImageButton;
import Client.Character.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 提示窗口界面
 */
public class InstructionGUI extends JFrame {
    private String imageRoot;
    public InstructionGUI(String s) {
        super("The King of Fighters");
        imageRoot = s;
        setSize(955 / 5 * 2,700 / 5 * 2);
        launchFrame();


        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    class Instruction extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Toolkit tk =Toolkit.getDefaultToolkit();
            Image poster = tk.getImage(Character.class.getClassLoader().getResource(imageRoot));//加载背景
            g.drawImage(poster,0,0,poster.getWidth(null) / 5 * 2,
                    poster.getHeight(null) / 5 * 2,null);
            repaint();
        }
    }

    public void launchFrame() {
        Instruction instruction = new Instruction();
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
//        InstructionGUI i = new InstructionGUI("images/instruction/instruction.png");
//    }
}
