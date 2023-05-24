package observer.jbutton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
    JFrame jFrame;

    public static void main(String[] args) {
        Test test = new Test();
        test.go();
    }

    public void go() {
        jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setSize(300,200);
        JButton jButton = new JButton("should I do it ?");
        jButton.addActionListener(new AngelListner());
        jButton.addActionListener(new DevilListner());
        jFrame.getContentPane().add(BorderLayout.CENTER, jButton);

    }

    class AngelListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Don't do it, you might regret it");
        }
    }

    class DevilListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Come on, do it");
        }
    }

}
